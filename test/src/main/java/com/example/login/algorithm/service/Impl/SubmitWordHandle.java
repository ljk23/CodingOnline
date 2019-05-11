package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.SubmitWordMapper;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

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
    public void UnZip(String zipPath,String outPath) throws IOException{
        ZipFile zipFile = new ZipFile(zipPath,"GBK");//压缩文件的实列,并设置编码
        //获取压缩文中的所以项
        for(Enumeration<ZipEntry> enumeration = zipFile.getEntries(); enumeration.hasMoreElements();)
        {
            ZipEntry zipEntry = enumeration.nextElement();//获取元素
            //排除空文件夹
            if(!zipEntry.getName().endsWith(File.separator))
            {
                System.out.println("正在解压文件:"+zipEntry.getName());//打印输出信息
                //创建解压目录
                File f = new File(outPath+zipEntry.getName().substring(0, zipEntry.getName().lastIndexOf(File.separator)));
                //判断是否存在解压目录
                if(!f.exists())
                {
                    f.mkdirs();//创建解压目录
                }
                OutputStream os = new FileOutputStream(outPath+zipEntry.getName());//创建解压后的文件
                BufferedOutputStream bos = new BufferedOutputStream(os);//带缓的写出流
                InputStream is = zipFile.getInputStream(zipEntry);//读取元素
                BufferedInputStream bis = new BufferedInputStream(is);//读取流的缓存流
                CheckedInputStream cos = new CheckedInputStream(bis, new CRC32());//检查读取流，采用CRC32算法，保证文件的一致性
                byte [] b = new byte[1024];//字节数组，每次读取1024个字节
                //循环读取压缩文件的值
                while(cos.read(b)!=-1)
                {
                    bos.write(b);//写入到新文件
                }
                cos.close();
                bis.close();
                is.close();
                bos.close();
                os.close();
            }
            else
            {
                //如果为空文件夹，则创建该文件夹
                new File(outPath+zipEntry.getName()).mkdirs();
            }
        }
        System.out.println("解压完成");
        zipFile.close();
    }

    /** 第一步-----进行编译 */
    public Boolean Compiler(String filepath) throws IOException{
        /**  输出重定向 */
        System.setErr(new PrintStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/error.txt"));
        PrintStream err = System.err;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int results = compiler.run(null, null, null, filepath);
        if(results==0){
            /**  编译完成后删除源代码文件  */
            deleteCode(filepath);
            return true;
        }
        else{
          //  deleteCode(filepath);
            return false;
        }
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
    public Boolean Answer(Integer problemid,MultipartFile file) throws IOException{
        /**  执行java的.class文件 */
        System.out.println(file.getOriginalFilename());

        String cls=file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-5);
        String cmd="java -cp /Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+" "+cls;
        Runtime run = Runtime.getRuntime();//获取与当前平台进行交互的实例
        Process process = run.exec(cmd);//当前平台执行对应命令
        /**  输入流文件 */
        new Thread() {
            public void run() {
                /** 从控制台输入 */
                OutputStream out = process.getOutputStream();
                PrintWriter writer = new PrintWriter(out);
                //    Scanner scanner = new Scanner(System.in);

                /** 从文件中读取输入 */
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream("/Users/tp5admin/compiler/src/lll.txt");
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
            }
        }.start();

        /**   重定向输出流文件 */
        PrintStream pss=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt"));
        System.setOut(pss);

        /**  输出执行结果 */
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取执行进程的输入流
        String runInfo = null;
        while (null != (runInfo = br.readLine())) {//读取执行结果并写入到output.txt中
            System.out.println(runInfo);
        }
        /**  重定向输出去掉空格和换行的运行结果文件----一方面----- */
        PrintStream after=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt"));
        System.setOut(after);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/result.txt");

        /**  重定向输出去掉空格和换行的运行结果文件----二方面----- */
        PrintStream correct=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/canceltab1.txt"));
        System.setOut(correct);
        CancelTab("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/1.txt");

        /**  重定向输出最终的结果----保存到word.txt中----- */
        PrintStream isaccept=new PrintStream(new FileOutputStream("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/word.txt"));
        System.setOut(isaccept);
        /**  将结果和正确答案的txt文件比较----并返回结果---- */
        if(isSameFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/buffoutput/cancelTabresult.txt","/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/outputset/canceltab1.txt"))
            return true;
        else
            return false;
    }
}
