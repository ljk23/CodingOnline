package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.SubmitWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static sun.util.locale.UnicodeLocaleExtension.isKey;

@Component
public class SubmitWordHandle implements SubmitWordMapper {
    @Autowired
    private SubmitWordHandle submitWordHandle;

    private static Boolean flag;
    private char ch;
    public void setFlag(Boolean flag){   /** 去掉注释所用到的标记量 */
        this.flag=flag;
    }

    /** 将文件写入wordinput.txt */
    public String writeWordIntoFile(String submitcontent,int userid,String wordresult) throws IOException {
        /**  将submitcontent输入wordinput.txt */
        /** 保存文件地址 */
        String filename=String.valueOf(userid);
        File file = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "wordinput.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        byte[] contentInBytes = submitcontent.getBytes();
        fop.write(contentInBytes);
        fop.flush();
        fop.close();
        /** 预处理  */
        String fileurl = "/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "wordinput.txt";
        submitWordHandle.preHandleWordFile(fileurl, filename);

        /** 词法分析 */
        File file1 = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "wordnoninput.txt");//定义一个file对象，用来初始化FileReader
        InputStreamReader inputStreamReader1 = new InputStreamReader(new FileInputStream(file1));
        BufferedReader bf1 = new BufferedReader(inputStreamReader1);
        String str1;

        /** 按照行写入 */
        File writeName = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "wordoutput.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
        writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
        FileWriter writer = new FileWriter(writeName);
        BufferedWriter out1 = new BufferedWriter(writer);

        /**  写入错误log */
        File errorName = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "worderror.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
        errorName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
        FileWriter error = new FileWriter(errorName);
        BufferedWriter errorout = new BufferedWriter(error);

        int linenums = 0;
        while ((str1 = bf1.readLine()) != null) {
            str1 = str1 + ' ';
            linenums++;
            wordresult=submitWordHandle.analyze(str1, out1, linenums,errorout,wordresult);
        }
        return wordresult;
     }
    /** 处理注释和空格 */
    public Boolean preHandleWordFile(String fileurl,String filename) throws IOException{
        /**  源文件的预处理 */
        File nonfile=new File(fileurl);
        InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(nonfile));
        BufferedReader bf=new BufferedReader(inputStreamReader);
        //按照行读取字符串
        /** 按照行写入 */
        OutputStreamWriter os=new OutputStreamWriter(new FileOutputStream(new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/"+filename+"wordnoninput.txt")));
        BufferedWriter bw=new BufferedWriter(os);
        PrintWriter out=new PrintWriter(bw);
        String str;
        new SubmitWordHandle().setFlag(false);
        while((str=bf.readLine())!=null){
            str=dealNote(str,flag);
            if(str.length()==0)  continue;
            out.println(str);
        }
        bf.close();
        inputStreamReader.close();
        /** 去掉注释  */
        bw.close();
        os.close();
        out.close();
        return true;
    }
    /** 处理注释的方法 */
    public  static  String dealNote(String str,Boolean flag){
        Matcher matcher= Pattern.compile("//").matcher(str);  //含有//

        if(flag==false){
            if(matcher.find()){
                str=str.substring(0,matcher.start());
                return str;
            }
            else if((str.indexOf("/*")!=-1) && (str.indexOf("*/")!=-1) && str.indexOf("/*")<(str.indexOf("*/"))){
                str=str.substring(0,str.indexOf("/*"));
                return str;
            }else if((str.indexOf("/*")!=-1) && (str.indexOf("*/")==-1)){
                str=str.substring(0,str.indexOf("/*"));
                boolean a;
                new SubmitWordHandle().setFlag(true);
                return str;
            }
            return str;
        }
        else if(flag==true){
            if((str.indexOf("*/")!=-1)){
                new SubmitWordHandle().setFlag(false);
                str=str.substring(str.indexOf("*/")+2,str.length());
                return str;
            }
            else{
                str="";
                return str;
            }
        }
        return str;
    }
    /** 词法分析的程序  */
    public  String analyze(String chars,BufferedWriter out,int linenums,BufferedWriter errorout,String wordresult) throws IOException {
        String arr = "";
        for(int i = 0;i<chars.length();i++) {
            int rownums=i+1;
            ch = chars.charAt(i);
            arr = "";
            if(ch == ' '||ch == '\t'||ch == '\n'||ch == '\r'){}
            else if(isLetter(ch)){
                while(isLetter(ch)||isDigit(ch)){
                    arr += ch;
                    ch = chars.charAt(++i);
                }
                //回退一个字符
                i--;
                if(isKey(arr)){
                    //关键字
                    out.write(arr+"\t4"+"\t关键字\t"+linenums+"\t"+rownums+",");
                    out.flush();
                    wordresult+=arr+"\t4"+"\t关键字\t"+linenums+"\t"+rownums+",";
                }
                else{
                    //标识符
                    out.write(arr+"\t4"+"\t标识符\t"+linenums+"\t"+rownums+",");
                    out.flush();
                    wordresult+=arr+"\t4"+"\t标识符\t"+linenums+"\t"+rownums+",";
                }
            }
            else if(isDigit(ch)||(ch == '.'))
            {
                while(isDigit(ch)||(ch == '.'&&isDigit(chars.charAt(++i))))
                {
                    if(ch == '.') i--;
                    arr = arr + ch;
                    ch = chars.charAt(++i);
                }
                //属于无符号常数
                out.write(arr+"\t5"+"\t常数\t"+linenums+"\t"+rownums+",");
                out.flush();
                wordresult+=arr+"\t5"+"\t常数\t"+linenums+"\t"+rownums+",";
            }
            else switch(ch){
                    //运算符
                    case '+':out.write(ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");out.flush();
                         wordresult+=ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+","; break;
                    case '-':out.write(ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");out.flush();
                         wordresult+=ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+","; break;
                    case '*':out.write(ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");out.flush();
                         wordresult+=ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+","; break;
                    case '/':out.write(ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");out.flush();
                         wordresult+=ch+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+","; break;
                    //分界符
                    case '(':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case ')':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                          out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case '[':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                          out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case ']':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                          out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                            wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case ';':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                         out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case '{':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");
                          out.flush();out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    case '}':out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                           out.write(ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+",");out.flush();
                        wordresult+=ch+"\t3"+"\t分界符\t"+linenums+"\t"+rownums+","; break;
                    /** 运算符   */
                    case '=':{
                        ch = chars.charAt(++i);
                        if(ch == '='){
                            out.write("=="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="=="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write("="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    case '!':{
                        ch = chars.charAt(++i);
                        if(ch == '='){
                            out.write("!="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="!="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write("!"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="!"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    case '&':{
                        ch = chars.charAt(++i);
                        if(ch == '&'){
                            out.write("&&"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="&&"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write("&"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="&"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    case '|':{
                        ch = chars.charAt(++i);
                        if(ch == '|') {
                            out.write("||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write("|"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    case ':':{
                        ch = chars.charAt(++i);
                        if(ch == '='){
                            out.write(":="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            out.flush();
                        }
                        else {
                            out.write(":"+"\t6"+"\t其他字符\t"+linenums+"\t"+rownums+",");
                            wordresult+=":"+"\t6"+"\t其他字符\t"+linenums+"\t"+rownums+",";
                            out.flush();
                            i--;
                        }
                    }break;
                    case '>':{
                        ch = chars.charAt(++i);
                        if(ch == '='){
                            out.write(">="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write(">"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    case '<':{
                        ch = chars.charAt(++i);
                        if(ch == '='){
                            out.write("<="+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                        }
                        else {
                            out.write("<"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",");
                            out.flush();
                            wordresult+="||"+"\t2"+"\t运算符\t"+linenums+"\t"+rownums+",";
                            i--;
                        }
                    }break;
                    //无识别
                    default: {
                        out.write(ch+"\t6"+"\t无识别符\t"+linenums+"\t"+rownums+",");
                        errorout.write("第"+linenums+"行第"+rownums+"列"+ch+"未识别的符号,");
                        out.flush();
                        errorout.flush();
                        wordresult+=ch+"\t6"+"\t无识别符\t"+linenums+"\t"+rownums+",";
                    }
                }
        }
        return wordresult;   /** 返回结果  */
    }
    /** 判断是否有错  */
    public String judgeIserror(Integer userid) throws IOException{
        String filename=String.valueOf(userid);
        String  errorInfo="",errorInfo1;
        File errorlength = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "worderror.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
        System.out.println(userid+"文件的大小是"+errorlength.length());
        if(errorlength.length()==0)
            return null;
        else{
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(errorlength));
            BufferedReader bf1 = new BufferedReader(inputStreamReader);
            while ((errorInfo1 = bf1.readLine()) != null){
                errorInfo+=errorInfo1+"\n";
            }
            return errorInfo;
        }
    }
}
