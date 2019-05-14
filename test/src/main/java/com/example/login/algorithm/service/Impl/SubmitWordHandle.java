package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.SubmitWordMapper;
import com.example.login.problem.dao.entity.Probleminfo;
import com.example.login.problem.dao.mapper.ProbleminfoMapper;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import com.sun.tools.javac.resources.compiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.tools.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static sun.util.locale.UnicodeLocaleExtension.isKey;

@Component
public class SubmitWordHandle implements SubmitWordMapper {
    @Autowired
    private ProbleminfoMapper probleminfoMapper;
    @Autowired
    private SubmitWordHandle submitWordHandle;
    @Autowired
    private CommonHandle commonHandle;
    @Autowired
    private  SubmitinfoMapper submitinfoMapper;

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


    /**  将代码写入文件当中 */
    public String WriteCideIntoFile(String contents,String codingLanguage) throws IOException{
        /**  编码是java文件 */
        /**  将submitcontent输入wordinput.txt */
        /** 保存文件地址 */
        File file=null;
        String codingpath="";
        if(codingLanguage.equals("java"))
        {
                codingpath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.java";
                file = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.java");
        }else{
                codingpath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.cpp";
                file = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.cpp");
        }

            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            byte[] contentInBytes = contents.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        return codingpath;
    }

    /**  去掉代码中的空格 */
    public static void CancelTab(String filename) throws IOException {
        String str = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        while ((str = br.readLine()) != null) {
            String s = str;
            s= s.replace("\r", "");
            s= s.replace("\t", "");
            s= s.replace(" ", "");
            System.out.print(s);
        }
    }

    /**  比对输出流结果和正确的结果是否相同  */
    public static boolean isSameFile(String fileName1,String fileName2){
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(fileName1);
            fis2 = new FileInputStream(fileName2);

            int len1 = fis1.available();//返回总的字节数
            int len2 = fis2.available();

            if (len1 == len2) {//长度相同，则比较具体内容
                //建立两个字节缓冲区
                byte[] data1 = new byte[len1];
                byte[] data2 = new byte[len2];

                //分别将两个文件的内容读入缓冲区
                fis1.read(data1);
                fis2.read(data2);

                //依次比较文件中的每一个字节
                for (int i=0; i<len1; i++) {
                    //只要有一个字节不同，两个文件就不一样
                    if (data1[i] != data2[i]) {
                        System.out.println("文件内容不一样");
                        return false;
                    }
                }
                System.out.println("两个文件完全相同");
                return true;
            } else {
                //长度不一样，文件肯定不同
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {//关闭文件流，防止内存泄漏
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    //忽略
                    e.printStackTrace();
                }
            }
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    //忽略
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /** 上传代码到wordcode文件夹中  */
    public String uploadWordCode(MultipartFile file){
        if (file.isEmpty()) {
            System.out.println("上传失败，请选择文件");
        }

        String fileName = file.getOriginalFilename();
        String filePath ="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/";
        File dest=new File(filePath+fileName);
        try{
            file.transferTo(dest);
            System.out.println("上传成功");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return filePath+fileName;
    }

    /**  如果是文件夹进行编译 */
    public Boolean UnZip(String zipPath, String descDir) throws IOException{
        File zipFile = new File(zipPath);
        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        java.util.zip.ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, Charset.forName("gbk"));//防止中文目录，乱码
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                java.util.zip.ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**  第一步----编译文件 ----编译java文件--- */
    public Boolean Compiler(String filepath) throws IOException{
        /**  输出重定向 */
        System.setErr(new PrintStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt"));
        PrintStream err = System.err;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int results = compiler.run(null, null, null, filepath);
        if(results==0){
            /**  编译完成后删除源代码文件  */
            deleteCode(filepath);
            return true;
        }
        else{
           return false;
        }
    }

    /**  第一步----编译文件 ----编译cpp件--- */
    public Boolean CompilerCpp(String filepath) throws IOException{
        /**  输出重定向 */
        PrintStream correct=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt"));
        System.setOut(correct);
        String cmd = "g++ -Wall -g  -o main "+filepath;
        Process process = Runtime.getRuntime().exec(cmd,null,new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/")); // 执行编译指令
        if(process!=null){
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));//获取执行进程的输入流
            String runInfo = null;
            while (null != (runInfo = br.readLine())) {//读取执行结果并写入到output.txt中
                System.out.println(runInfo);
                return false;
            }
            return true;
        }
        return false;
    }

    /** 第一步-----进行编译java文件夹 */
    public Boolean CompilerFiles(String filepath) throws IOException{
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 建立DiagnosticCollector对象
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

       // filepath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+filepath;
        File file = new File(filepath);		//获取其file对象
        File[] fs = file.listFiles();
        for(File f:fs){
            String fileName=f.getAbsolutePath();
            if(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equals("java")){
                System.out.println(f);
                try{
                    Iterable<String> options = Arrays.asList("-sourcepath", "/Users/tp5admin/Desktop/sometime");

                    Iterable<? extends JavaFileObject> compilationUnits = fileManager
                            .getJavaFileObjectsFromStrings(Arrays.asList(f.getAbsolutePath()));
                    JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
                    // 编译源程序
                    boolean success = task.call();
                    fileManager.close();
                    System.out.println((success)?"编译成功":"编译失败");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else  continue;
        }
        /**  删除编译生成的多余文件  */

        return true;
    }

    /** 第一步-----进行编译c++文件夹 */
    public Boolean CompilerAppFiles(String filepath) throws IOException{
        PrintStream correct=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt"));
        System.setOut(correct);
        String cmd="g++ -Wall -g  -o main ";
        /**  遍历文件夹，编译到main函数中 */
        File file = new File(filepath);		//获取其file对象
        File[] fs = file.listFiles();
        for(File f:fs){
            String fileName=f.getName();
            if(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).equals("cpp")){
                cmd+=f.getAbsolutePath()+" ";
            }
        }
        cmd="g++ -Wall -g -o main /Users/tp5admin/Desktop/ljk/greet.cpp /Users/tp5admin/Desktop/ljk/app.cpp";
        Process proc = Runtime.getRuntime().exec(cmd,null,new File(filepath)); // 执行编译指令
        if(proc!=null){
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));//获取执行进程的输入流
            String runInfo = null;
            while (null != (runInfo = br.readLine())) {//读取执行结果并写入到output.txt中
                System.out.println(runInfo);
                return true;
            }
            return true;
        }
        return false;
    }

    /**  删除源代码文件 */
    public void deleteCode(String filename){
        File file=new File(filename);
        if(file.exists() && file.isFile()) {
            file.delete();
            System.out.println("删除文件成功");
        }
    }

    /** 第二步----黑盒测试  */
    public Integer Answer(Integer problemid,String filename,Submitinfo submitinfo) throws IOException {

        /**  获得输入输出测试用例写入到input文件中  */
        Probleminfo probleminfo = probleminfoMapper.selectByProblemName(submitinfo.getSubmitname());
        /**  执行java的.class文件 */
        System.out.println(filename);
        String fileName = filename;
        String foreName = fileName.substring(0, fileName.lastIndexOf("."));   /**  获得文件的名称 */
        String cls = "main";   /**   主函数入口 */
        /**  判断文件的格式 */
        String cmd = "";
        if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("zip")) {
            cmd = "java -cp /Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/" + foreName + "/" + " " + cls;
        } else {
            cmd = "java -cp /Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/" + " " + cls;
        }

        /**  编译时间-------------------------------------- */
        final long timeout = 10000; // 限制的执行时间（毫秒）
        final long starttime = System.currentTimeMillis();
        final long memoryout = 100000;  //限制内存
        /**  编译时消耗的内存-------------------------------- */
        Runtime runmemory = Runtime.getRuntime();
        runmemory.gc();
        //  System.out.println("time: " + (new Date()));
        // 获取开始时内存使用量
        long startMem = runmemory.totalMemory() - runmemory.freeMemory();
        // System.out.println("memory> total:" + runmemory.totalMemory() + " free:" + runmemory.freeMemory() + " used:" + startMem);


        Runtime run = Runtime.getRuntime();//获取与当前平台进行交互的实例
        Process process = run.exec(cmd);//当前平台执行对应命令


        /** 从控制台输入 */
        OutputStream out = process.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        //    Scanner scanner = new Scanner(System.in);

        /**  重定向输出到lll文件中 */
        try {
            PrintStream input = new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/inputset/input.txt"));
            System.setOut(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(probleminfo.getProbleminput());


        /** 从文件中读取输入 */
        FileInputStream fis = null;
        try {  /**  有改动 */
            fis = new FileInputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/inputset/input.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setIn(fis);
        Scanner scanner = new Scanner(System.in);

        String str = null;
        while (scanner.hasNextLine()) {
            writer.println(scanner.nextLine());
            writer.flush();
        }


        /**  输出执行结果 */
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取执行进程的输入流
        String runInfo = null;
        while (null != (runInfo = br.readLine())) {//读取执行结果并写入到reslut.txt中
            commonHandle.reWriteFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt", runInfo);
        }

        /**  判断有无超时间 */
        if (System.currentTimeMillis() - starttime > timeout) {
            // 超时
            process.destroy();
            submitinfo.setWordresult("超时");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            return 4;
        } else {
            String time = String.valueOf((System.currentTimeMillis() - starttime));
            submitinfo.setWordresult(time);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
        }

        /**  判断有无超内存 */
        long ll = (run.totalMemory() - run.freeMemory()) / 1024;
        if (ll > memoryout) {
            process.destroy();
            submitinfo.setSynaxresult("超内存");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            return 5;

        } else {
            long endMem = run.totalMemory() - run.freeMemory();
            submitinfo.setSynaxresult(String.valueOf((endMem - startMem) / 1024));
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
        }

        /**  重定向输出去掉空格和换行的运行结果文件----一方面----- */
        PrintStream after = new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt"));
        System.setOut(after);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt");

        /**  重定向输出去掉空格和换行的运行结果文件----二方面----- */
        commonHandle.reWriteFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/output.txt", probleminfo.getProblemoutput());
        PrintStream afterout = new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/cancelTaboutput.txt"));
        System.setOut(afterout);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/output.txt");

        /**  重定向输出最终的结果----保存到word.txt中----- */
        PrintStream isaccept = new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/word.txt"));
        System.setOut(isaccept);
        /**  将结果和正确答案的txt文件比较----并返回结果---- */
        if (isSameFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt", "/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/cancelTaboutput.txt")) {
            /**  删除编译文件 */
            delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
            return 1;
        } else {
            /**  删除编译文件 */
            delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
            return 2;
        }
    }

    /** 第二步----黑盒测试  */
    public Integer AnswerCpp(Integer problemid,String filename,Submitinfo submitinfo) throws IOException{
        /**  获得输入输出测试用例写入到input文件中  */
        Probleminfo probleminfo=probleminfoMapper.selectByProblemName(submitinfo.getSubmitname());


     /*   *  重新定位到控制台
        PrintStream printStream=System.out;
        System.setOut(printStream);*/
        /**  入口函数为main */
        String cmd ="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+filename+"main";
        Process process = Runtime.getRuntime().exec(cmd);

        /**  编译时间-------------------------------------- */
        final long timeout = 10000; // 限制的执行时间（毫秒）
        final long starttime = System.currentTimeMillis();
        final long memoryout = 100000;  //限制内存
        /**  编译时消耗的内存-------------------------------- */
        Runtime runmemory = Runtime.getRuntime();
        runmemory.gc();
        //  System.out.println("time: " + (new Date()));
        // 获取开始时内存使用量
        long startMem = runmemory.totalMemory() - runmemory.freeMemory();
        // System.out.println("memory> total:" + runmemory.totalMemory() + " free:" + runmemory.freeMemory() + " used:" + startMem);

        /**  输入流文件 */

                /** 从控制台输入 */
                OutputStream out = process.getOutputStream();
                PrintWriter writer = new PrintWriter(out);
                //    Scanner scanner = new Scanner(System.in);

                /**  重定向输出到lll文件中 */
                try{
                    PrintStream input=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/inputset/input.txt"));
                    System.setOut(input);
                }catch (IOException e){
                    e.printStackTrace();
                }
                System.out.println(probleminfo.getProbleminput());


                /** 从文件中读取输入 */
                FileInputStream fis = null;
                try{  /**  有改动 */
                    fis = new FileInputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/inputset/input.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.setIn(fis);
                Scanner scanner = new Scanner(System.in);

                String str = null;
                while (scanner.hasNextLine()) {
                    writer.println(scanner.nextLine());
                    writer.flush();
                }


        /**  输出执行结果 */
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取执行进程的输入流
        String runInfo = null;
        while (null != (runInfo = br.readLine())) {//读取执行结果并写入到reslut.txt中
            commonHandle.reWriteFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt",runInfo);
        }

        /**  判断有无超时间 */
        if (System.currentTimeMillis() - starttime > timeout) {
            // 超时
            process.destroy();
            submitinfo.setWordresult("超时");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            return 4;
        } else {
            String time = String.valueOf((System.currentTimeMillis() - starttime));
            submitinfo.setWordresult(time);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
        }

        /**  判断有无超内存 */
        long ll = (runmemory.totalMemory() - runmemory.freeMemory()) / 1024;
        if (ll > memoryout) {
            process.destroy();
            submitinfo.setSynaxresult("超内存");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            return 5;

        } else {
            long endMem = runmemory.totalMemory() - runmemory.freeMemory();
            submitinfo.setSynaxresult(String.valueOf((endMem - startMem) / 1024));
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
        }


        /**  重定向输出去掉空格和换行的运行结果文件----一方面----- */
        PrintStream after=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt"));
        System.setOut(after);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt");

        /**  重定向输出去掉空格和换行的运行结果文件----二方面----- */
        commonHandle.reWriteFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/output.txt",probleminfo.getProblemoutput());
        PrintStream afterout=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/cancelTaboutput.txt"));
        System.setOut(afterout);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/output.txt");

        /**  重定向输出最终的结果----保存到word.txt中----- */
        PrintStream isaccept=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/word.txt"));
        System.setOut(isaccept);
        /**  将结果和正确答案的txt文件比较----并返回结果---- */
        if(isSameFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt","/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/cancelTaboutput.txt"))
        {
            /**  删除编译文件 */
            delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
            return 1;
        }
        else
        {
            /**  删除编译文件 */
            delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
            return 2;
        }
    }

    /**  完成文件夹编译后删除多余文件 */
    public void deleteFiles(String folderPath) throws Exception{
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除所有文件  */
    public  boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /** 删除文件夹*/
    public static void delFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**  判断能否编译成功 */
    public Integer JudgeResult(String codingFile, Submitinfo submitinfo) throws IOException {
        /**  获得文件的后缀名  */
        String fileType = codingFile.substring(codingFile.lastIndexOf(".") + 1, codingFile.length());
        String filename = codingFile.substring(codingFile.lastIndexOf("/") + 1, codingFile.length());
        if (fileType.equals("java")) {  /**  java文件 */
            if (Compiler(codingFile)) {
                System.out.println("编译成功");
                /** 第二步-----用例测试----  */
                /** first ---通过 problemId 拿到输入测试集-----  **/
                int result=Answer(1, filename,submitinfo);
                System.out.println("答案是"+result);
                /**  删除编译文件 */
                delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                if(result==1) return 1;
                else if(result==2)  return 2;
                else if(result==4)  return 4;
                else return 5;
            } else {   /** 编译失败  */
                /**  删除编译文件 */
                delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                System.out.println("编译错误");
                return 3;
            }
        }
        else {
            if (CompilerCpp(codingFile)) {  /**  cpp文件 */
                System.out.println("编译成功");
                /** 第二步-----用例测试----  */
                /** first ---通过 problemId 拿到输入测试集-----  **/
                filename="";
                int result=AnswerCpp(1, filename,submitinfo);
                /**  删除编译文件 */
                delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                if(result==1) return 1;
                else if(result==2)  return 2;
                else if(result==4)  return 4;
                else return 5;
            } else {   /** 编译失败  */
                /**  删除编译文件 */
                delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                System.out.println("编译错误");
                return 3;
            }
        }
    }
}
