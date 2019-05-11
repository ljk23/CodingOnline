package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.SubmitSynaxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static sun.util.locale.UnicodeLocaleExtension.isKey;

@Component
public class SubmitSynaxHandle implements SubmitSynaxMapper {
    @Autowired
    private SubmitSynaxHandle submitSynaxHandle;

    public  String Synax(String str,Integer filenameid){
        String filename=String.valueOf(filenameid);
        String resultInfo=null;
        try{
            a c=new a();
            resultInfo=c.mainMethod(str,filename);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultInfo;
    }

    static class SYS
    {
        String fuhao;
        String op1;
        String op2;
        Node p1;
    }
    static class Node
    {
        String result;
        int  Address_Num;
    }
    static class a
    {
        Vector shuruliu=new Vector();
        Vector shuruliubeifen=new Vector();
        Vector fenxizhan=new Vector();
        Vector the_baoliuzi=new Vector();
        Vector jiefu=new Vector();
        Vector yunsuanfu=new Vector();
        Vector result=new Vector();

        int while_endsign=0;
        static int count=0;
        static int count_sign=0;
        int if_oversign=0;
        int else_beginsign=0;
        int while_beginsign=10;
        int huibianjump[]=new int[50];
        int sign=0;
        static SYS siyuanshi[]=new SYS[40];

        public String mainMethod(String sFromFrame,String filename) throws Exception {

            count=0;
            String returnString = "";
            a b=new a();
            for(int k=0;k<40;k++) {
                siyuanshi[k]=new SYS();
                siyuanshi[k].p1=new Node();
            }
            String l="";
            String d="";
            String yuan="";
            b.yunsuanfu.add("+");
            b.yunsuanfu.add("-");
            b.yunsuanfu.add("*");
            b.yunsuanfu.add("/");
            b.yunsuanfu.add(">");
            b.yunsuanfu.add("<");
            b.yunsuanfu.add("=");
            b.yunsuanfu.add("%");
            b.jiefu.add("(");
            b.jiefu.add(")");
            b.jiefu.add("{");
            b.jiefu.add("}");
            b.jiefu.add(";");
            b.the_baoliuzi.add("else");
            b.the_baoliuzi.add("int");
            b.the_baoliuzi.add("if");
            b.the_baoliuzi.add("while");
            b.the_baoliuzi.add("char");
            b.the_baoliuzi.add("float");
            b.the_baoliuzi.add("double");
            b.the_baoliuzi.add("main");
            String s=sFromFrame;
            boolean sign=false;
            String ls="";
            for(int i = 0;i<s.length();i++) {
                ls=s.substring(i,i+1);
                l=l+ls;
                while((ls.compareToIgnoreCase("a")>=0)&&(ls.compareToIgnoreCase("z")<=0)) {
                    sign=true;
                    i++;
                    if(i<s.length()) {
                        ls=s.substring(i,i+1);
                    }
                    if(i==s.length()||ls.equals("}")||ls.equals("<")||ls.equals("{")
                            ||ls.equals(">")||ls.equals(",")||ls.equals(";")||ls.equals("=")
                            ||ls.equals("(")||ls.equals(")")||ls.equals(" ")||ls.equals("*")
                            ||ls.equals("/")||ls.equals("+")||ls.equals("-")
                            ||ls.equals("\n")||ls.equals("\t"))
                    {
                        break;
                    }
                    else {
                        l=l+ls;
                    }
                }
                if(isNum(ls)) {
                    System.out.println("number "+ls);
                    sign=true;
                    while(true) {
                        i++;
                        ls=s.substring(i,i+1);
                        if(ls.equals("}")||ls.equals(",")||ls.equals(";")||ls.equals("=")
                                ||ls.equals("(")||ls.equals(")")||ls.equals(" ")||ls.equals("*")
                                ||ls.equals("/")||ls.equals("\n")||ls.equals("\t"))
                        {
                            break;
                        }
                        else{
                            l = l + ls;
                        }
                    }
                }
                if(isNum(l)){
                    b.shuruliu.add(l);
                    b.shuruliubeifen.add(l);
                    l="";
                    sign=false;
                }
                else if(b.the_baoliuzi.contains(l)){
                    b.shuruliu.add(l);
                    b.shuruliubeifen.add(l);
                    l="";
                    sign=false;
                }
                else if(!b.the_baoliuzi.contains(l)&&sign==true)
                {
                    b.shuruliu.add(l);
                    b.shuruliubeifen.add(l);
                    l="";
                    sign=false;
                }
                if(b.yunsuanfu.contains(ls)||b.jiefu.contains(ls))
                {
                    b.shuruliu.add(ls);
                    b.shuruliubeifen.add(ls);
                    l="";
                }
                if(ls.equals(" ")||ls.equals("\n")||ls.equals("\t"))
                {
                    l="";
                }
            }
            b.shuruliu.add("$");
            b.shuruliubeifen.add("$");
            System.out.println("**************************************************************");
            System.out.println("**************************************************************");
         //  returnString += "lex begin ... ... ...\n";
            b.cifafenxi();
         //  returnString += "lex psss !!!!!!\n";
            System.out.println("**************************************************************");
            System.out.println("**************************************************************");
         //   returnString += "LL1 begin ... ... ...\n";
            b.LL1(filename);
         //   returnString += "LL1 pass !!!!!!\n";
         //   returnString += "yuyi begin ... ... ...\n";
            b.siyuanshi();
         //   returnString += "yuyi pass !!!!!!\n";
         //   returnString += "四元式结果如下：\n";
            System.out.println("**************************************************************");
            System.out.println("**************************************************************");
            System.out.println("四元式结果如下：");
            System.out.println();
            System.out.println("测试"+count);
            for(int i=0;i<count;i++) {
                if((siyuanshi[i].fuhao).equals("j")) {
                    returnString += i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+");";
                    System.out.println(i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+")");
                }
                else if((siyuanshi[i].fuhao).equals(">")) {
                    returnString += i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+");";
                    System.out.println(i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+")");
                }
                else if((siyuanshi[i].fuhao).equals("<")) {
                    returnString += i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+");";
                    System.out.println(i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.Address_Num+")");
                }
                else {
                    returnString += i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.result+");";
                    System.out.println(i+"\t("+siyuanshi[i].fuhao+","+siyuanshi[i].op1+","+siyuanshi[i].op2+","+siyuanshi[i].p1.result+")");
                }
            }
            return returnString;
        }

        //数的判断函数
        public boolean isNum(String name)
        {
            Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
            if(pattern.matcher(name).matches()){
                return true;
            }
            return false;
        }

        ////标识符和保留字的判断函数
        public boolean isBaoliuzi(String name)
        {
            int length=name.length();
            boolean q=false;
            for(int i=0;i<length;i++)
            {
                String n=name.substring(i,i+1);
                if((n.compareToIgnoreCase("a")>=0)&&(n.compareToIgnoreCase("z")<=0))
                {
                    i++;
                    if(i<length)
                    {n=name.substring(i,i+1);}
                    while((n.compareToIgnoreCase("a")>=0)&&(n.compareToIgnoreCase("z")<=0))
                    {
                        i++;
                        if(i>=length)
                        {
                            q=true;
                            break;
                        }
                        if(i<length)
                        {
                            n=name.substring(i,i+1);
                        }
                    }
                    if(i==length||n.equals(",")||n.equals(";")||n.equals("=")||n.equals("(")||n.equals(")")
                            ||n.equals(" ")||n.equals("*")||n.equals("/")||n.equals("+")||n.equals("-"))
                    {
                        q=true;
                    }
                }
                else
                {
                    return false;
                }
            }
            return q;
        }
        //判断是否是终结符
        public boolean isZhongjiefu(String s_fenxizhan){
            return s_fenxizhan.equals(">")
                    ||s_fenxizhan.equals("else")
                    ||s_fenxizhan.equals("<")
                    ||s_fenxizhan.equals("float")
                    ||s_fenxizhan.equals("char")
                    ||s_fenxizhan.equals("if")
                    ||s_fenxizhan.equals(",")
                    ||s_fenxizhan.equals("=")
                    ||s_fenxizhan.equals("while")
                    ||s_fenxizhan.equals(";")
                    ||s_fenxizhan.equals("+")
                    ||s_fenxizhan.equals("*")
                    ||s_fenxizhan.equals("(")
                    ||s_fenxizhan.equals(")")
                    ||s_fenxizhan.equals("-")
                    ||s_fenxizhan.equals("/")
                    ||s_fenxizhan.equals("main")
                    ||s_fenxizhan.equals("{")
                    ||s_fenxizhan.equals("}")
                    ||s_fenxizhan.equals("int")
                    ||s_fenxizhan.equals("void")
                    ||s_fenxizhan.equals("i");
        }

        public void LL1(String filename) throws Exception{
            /**  将错误信息写入文件当中 */
            File file=new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/"+filename+"synaxerror.txt");
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);



            fenxizhan.add("MAIN_IN");
            fenxizhan.add("$");
            the_baoliuzi.add("if");
            the_baoliuzi.add("else");
            the_baoliuzi.add("int");
            the_baoliuzi.add("char");
            the_baoliuzi.add("float");
            the_baoliuzi.add("double");
            the_baoliuzi.add("string");
            String ls = null;
            //state表示当前跳到得非终结符
            int state = 0,k,sign = 0,hang = 0;
            //judge判断保留字
            boolean t = true,judge = false,isnum = false;
            while(fenxizhan.size()>0)
            {
                String s_fenxizhan = (String)fenxizhan.elementAt(0);
                String s_shuruliu = (String)shuruliu.elementAt(0);
                String S = s_shuruliu.substring(0,1);
                if(isBaoliuzi(s_shuruliu)) {
                    judge=true;
                }
                if(isNum(s_shuruliu)) {
                    isnum=true;
                }
                if(s_fenxizhan.equals("MAIN_IN"))
                    state=0;
                else if(s_fenxizhan.equals("Q"))
                    state=1;
                else if(s_fenxizhan.equals("U"))
                    state=2;
                else if(s_fenxizhan.equals("V"))
                    state=3;
                else if(s_fenxizhan.equals("V2"))
                    state=4;
                else if(s_fenxizhan.equals("K"))
                    state=5;
                else if(s_fenxizhan.equals("E"))
                    state=6;
                else if(s_fenxizhan.equals("e"))
                    state=7;
                else if(s_fenxizhan.equals("T"))
                    state=8;
                else if(s_fenxizhan.equals("t"))
                    state=9;
                else if(s_fenxizhan.equals("F"))
                    state=10;
                if((shuruliu.elementAt(0).equals("$"))&&(fenxizhan.elementAt(0).equals("$"))) {
                    fenxizhan.removeElementAt(0);
                    shuruliu.removeElementAt(0);
                    System.out.println("\n语法分析通过!");
                    break;
                }
                //if 处理终结符，else处理非终结符
                if( isZhongjiefu(s_fenxizhan)) {//终极符时的处理

                    if(s_fenxizhan.equals("else")
                            ||s_fenxizhan.equals(")")
                            ||s_fenxizhan.equals("{")
                            ||s_fenxizhan.equals("}")
                            ||s_fenxizhan.equals(";")) {

                        hang++;
                    }
                    if(isnum==true
                            ||s_fenxizhan.equals(s_shuruliu)
                            ||(s_fenxizhan.equals("i") && judge==true && !(the_baoliuzi.contains(s_shuruliu)))) {

                        fenxizhan.removeElementAt(0);
                        shuruliu.removeElementAt(0);
                        judge=false;
                        isnum=false;
                    }
                    else {
                        System.out.println();
                        fenxizhan.removeElementAt(0);
                        shuruliu.removeElementAt(0);
                        System.out.println("第"+(hang)+"行   error!");  bw.write("第"+(hang)+"行   error!\r\n");
                    }
                }
                else//非终极符时的处理
                {
                    switch(state) {
                        case 0:
                        {
                            if(s_shuruliu.equals("main")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"main");
                                fenxizhan.add(1,"(");
                                fenxizhan.add(2,")");
                                fenxizhan.add(3,"{");
                                fenxizhan.add(4,"Q");
                                fenxizhan.add(5,"K");
                                fenxizhan.add(6,"}");
                            }
                            else if(s_shuruliu.equals("int")||s_shuruliu.equals("void")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,s_shuruliu);
                                fenxizhan.add(1,"main");
                                fenxizhan.add(2,"(");
                                fenxizhan.add(3,")");
                                fenxizhan.add(4,"{");
                                fenxizhan.add(5,"Q");
                                fenxizhan.add(6,"K");
                                fenxizhan.add(7,"}");
                            }
                            else
                            {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                            }
                            break;
                        }
                        case 1:
                        {
                            if(s_shuruliu.equals("int")||s_shuruliu.equals("char")||s_shuruliu.equals("float")||s_shuruliu.equals("double"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"U");
                                fenxizhan.add(1,"V");
                                fenxizhan.add(2,";");
                            }
                            else
                            {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                            }
                            break;
                        }
                        case 2:
                        {
                            if(s_shuruliu.equals("int")
                                    ||s_shuruliu.equals("char")
                                    ||s_shuruliu.equals("float")
                                    ||s_shuruliu.equals("double")) {

                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,s_shuruliu);
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                            }
                            break;
                        }
                        case 3:
                        {
                            if(judge==true) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"i");
                                fenxizhan.add(1,"V2");
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                            }
                            break;
                        }
                        case 4:
                        {
                            if(s_shuruliu.equals(";")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals(",")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,",");
                                fenxizhan.add(1,"i");
                                fenxizhan.add(2,"V2");
                            }
                            else if(s_shuruliu.equals("=")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"=");
                                fenxizhan.add(1,"E");
                            }
                            else if(s_shuruliu.equals("$")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                                
                            }
                            break;
                        }
                        case 5:
                        {
                            if(s_shuruliu.equals("}"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(judge==true
                                    && !(s_shuruliu.equals("else")
                                    ||s_shuruliu.equals("if")
                                    ||s_shuruliu.equals("while")
                                    ||s_shuruliu.equals("double")
                                    ||s_shuruliu.equals("int")
                                    ||s_shuruliu.equals("float")
                                    ||s_shuruliu.equals("char"))) {

                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"i");
                                fenxizhan.add(1,"=");
                                fenxizhan.add(2,"E");
                                fenxizhan.add(3,";");
                                fenxizhan.add(4,"K");
                                judge=false;
                            }
                            else if(s_shuruliu.equals("double")||s_shuruliu.equals("int")||s_shuruliu.equals("float")||s_shuruliu.equals("char"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"Q");
                                fenxizhan.add(1,"K");
                            }
                            else if(s_shuruliu.equals("if")||s_shuruliu.equals("while"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,s_shuruliu);
                                fenxizhan.add(1,"(");
                                fenxizhan.add(2,"E");
                                fenxizhan.add(3,")");
                                fenxizhan.add(4,"{");
                                fenxizhan.add(5,"K");
                                fenxizhan.add(6,"}");
                                fenxizhan.add(7,"K");
                            }
                            else if(s_shuruliu.equals("else"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,s_shuruliu);
                                fenxizhan.add(1,"{");
                                fenxizhan.add(2,"K");
                                fenxizhan.add(3,"}");
                                fenxizhan.add(4,"K");
                            }
                            else if(s_shuruliu.equals(";"))
                            {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,";");
                                fenxizhan.add(1,"K");
                            }
                            else
                            {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                                
                            }
                            break;
                        }
                        case 6:
                        {
                            if(judge == true || isnum == true) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"T");
                                fenxizhan.add(1,"e");
                                judge=false;
                                isnum=false;
                            }
                            else if(s_shuruliu.equals("(")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"T");
                                fenxizhan.add(1,"e");
                                sign=1;
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                            }
                            break;
                        }
                        case 7:
                        {
                            if(s_shuruliu.equals("+")||s_shuruliu.equals(">")||s_shuruliu.equals("<")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,s_shuruliu);
                                fenxizhan.add(1,"T");
                                fenxizhan.add(2,"e");
                            }
                            else if(s_shuruliu.equals("-")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"-");
                                fenxizhan.add(1,"T");
                                fenxizhan.add(2,"e");
                            }

                            else if(s_shuruliu.equals(")")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals(";")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals("$")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                                
                            }
                            break;
                        }
                        case 8:
                        {
                            if(judge==true||isnum==true) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"F");
                                fenxizhan.add(1,"t");
                                judge=false;
                                isnum=false;
                            }
                            else if(s_shuruliu.equals("(")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"F");
                                fenxizhan.add(1,"t");
                            }
                            else if(s_shuruliu.equals("=")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"F");
                                fenxizhan.add(1,"t");
                            }
                            else {
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                fenxizhan.removeElementAt(0);
                                
                            }
                            break;
                        }
                        case 9:
                        {
                            if(s_shuruliu.equals("+")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals("-")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals(">")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals("<")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals("*")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"*");
                                fenxizhan.add(1,"F");
                                fenxizhan.add(2,"t");
                            }
                            else if(s_shuruliu.equals("/")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"/");
                                fenxizhan.add(1,"F");
                                fenxizhan.add(2,"t");
                            }
                            else if(s_shuruliu.equals(")")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals(";")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else if(s_shuruliu.equals("$")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                            }
                            else {
                                fenxizhan.removeElementAt(0);
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                                
                            }
                            break;
                        }
                        case 10:
                        {
                            if(judge==true||isnum==true) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"i");
                                judge=false;
                                isnum=false;
                            }
                            else if(s_shuruliu.equals("(")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"(");
                                fenxizhan.add(1,"E");
                                fenxizhan.add(2,")");
                            }
                            else if(s_shuruliu.equals("=")) {
                                ls = (String)fenxizhan.elementAt(0);
                                fenxizhan.removeElementAt(0);
                                fenxizhan.add(0,"=");
                                fenxizhan.add(1,"E");

                            }
                            else {
                                fenxizhan.removeElementAt(0);
                                System.out.println("第"+hang+"行   error!");
                                bw.write("第"+(hang)+"行   error!\r\n");
                            }
                            break;
                        }
                    }
                }

                File f = new File("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/" + filename + "synaxoutput.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
                if(!f.exists()){
                    f.createNewFile();
                }
                BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(f,true));

                System.out.println();
                System.out.print("分析栈:");
                bufferedWriter.write("分析栈:");
                for(int i=0;i<fenxizhan.size();i++){
                    System.out.print(fenxizhan.elementAt(i));
                    bufferedWriter.write((String)fenxizhan.elementAt(i));
                }
                System.out.print("\t");
                System.out.print("输入流为:");
                bufferedWriter.write(";");
                bufferedWriter.write("输入流为:");
                for(int j=0;j<shuruliu.size();j++) {
                    System.out.print(shuruliu.elementAt(j));
                    bufferedWriter.write((String)shuruliu.elementAt(j));
                }
                System.out.print("\t");
                bufferedWriter.write(";");

                bufferedWriter.flush();
                bufferedWriter.close();
            }
              bw.close();
        }

        public void cifafenxi(){
            the_baoliuzi.add("if");
            the_baoliuzi.add("else");
            the_baoliuzi.add("int");
            the_baoliuzi.add("char");
            the_baoliuzi.add("float");
            the_baoliuzi.add("double");
            the_baoliuzi.add("string");
            int i=0;
            while(i<(shuruliu.size()-1)) {
                String name=(String)shuruliu.elementAt(i);
                if(the_baoliuzi.contains(name)) {
                    System.out.println(name+"       "+"is 保留字");
                    i++;
                }
                else if(jiefu.contains(name))
                {
                    System.out.println(name+"       "+"is 界符");
                    i++;
                }
                else if(yunsuanfu.contains(name))
                {
                    System.out.println(name+"       "+"is 运算符");
                    i++;
                }
                else if(isNum(name))
                {
                    System.out.println(name+"       "+"is 数字");
                    i++;
                }
                else
                {
                    System.out.println(name+"       "+"is 标识符");
                    i++;
                }
            }
        }

        public void siyuanshi() {
            String a="";
            int i=0;
            while(i<shuruliubeifen.size()) {
                String linshi1="";
                String linshi=(String)shuruliubeifen.elementAt(i);
                if(i<(shuruliubeifen.size()-1)) {
                    linshi1=(String)shuruliubeifen.elementAt(i+1);
                }
                if(linshi.equals("if")) {
                    if(((String)shuruliubeifen.elementAt(i+3)).equals(">")
                            ||((String)shuruliubeifen.elementAt(i+3)).equals("<")
                            ||((String)shuruliubeifen.elementAt(i+3)).equals("=")) {

                        siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+3);
                        siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                        siyuanshi[count].op2=(String)shuruliubeifen.elementAt(i+4);

                        huibianjump[sign]=count+2;
                        sign++;
                        siyuanshi[count].p1.Address_Num=count+2;
                        count++;

                        siyuanshi[count].fuhao="j";
                        siyuanshi[count].op1="_";
                        siyuanshi[count].op2="_";
                        else_beginsign=count;
                        count++;
                        i++;
                    }
                }
                else if(linshi.equals("else")) {

                    siyuanshi[count].fuhao="j";
                    siyuanshi[count].op1="_";
                    siyuanshi[count].op2="_";

                    if_oversign=count;
                    count++;
                    huibianjump[sign]=count;
                    sign++;
                    siyuanshi[else_beginsign].p1.Address_Num=count;
                    i++;
                }
                else if(linshi.equals("while")) {

                    if(((String)shuruliubeifen.elementAt(i+3)).equals(">")
                            ||((String)shuruliubeifen.elementAt(i+3)).equals("<")
                            ||((String)shuruliubeifen.elementAt(i+3)).equals("=")) {

                        while_beginsign=count;
                        siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+3);
                        siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                        siyuanshi[count].op2=(String)shuruliubeifen.elementAt(i+4);

                        huibianjump[sign]=count+2;
                        sign++;
                        siyuanshi[count].p1.Address_Num=count+2;
                        count++;
                        siyuanshi[count].fuhao="j";
                        siyuanshi[count].op1="_";
                        siyuanshi[count].op2="_";
                        while_endsign=count;
                        count++;
                        i++;
                    }
                }
                else if(((String)shuruliubeifen.elementAt(i)).equals("}") &&linshi1.equals("else")) {
                    i++;
                }
                else if(((String)shuruliubeifen.elementAt(i)).equals("}")&&(if_oversign!=0)) {
                    huibianjump[sign]=count;
                    sign++;
                    siyuanshi[if_oversign].p1.Address_Num=count;
                    if_oversign=0;
                    i++;
                }
                else if(linshi1.equals("=")) {
                    if(((String)shuruliubeifen.elementAt(i+5)).equals(";")) {
                        siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+3);
                        siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                        siyuanshi[count].op2=(String)shuruliubeifen.elementAt(i+4);
                        siyuanshi[count].p1.result=(String)shuruliubeifen.elementAt(i);
                        count++;
//
//					siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+1);
//					siyuanshi[count].op1=siyuanshi[count-1].p1.result;
//					siyuanshi[count].op2="_";
//	                siyuanshi[count].p1.result=(String)shuruliubeifen.elementAt(i);
//					count++;
                        i++;
                    }
                    else if(((String)shuruliubeifen.elementAt(i+3)).equals(";")) {
                        siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+1);
                        siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                        siyuanshi[count].op2="_";
                        siyuanshi[count].p1.result=(String)shuruliubeifen.elementAt(i);
                        count++;
                        i++;
                    }
                    else if(((String)shuruliubeifen.elementAt(i+7)).equals(";")) {
                        if(((String)shuruliubeifen.elementAt(i+3)).equals("+")
                                || ((String)shuruliubeifen.elementAt(i+3)).equals("-")){
                            siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+5);
                            siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+4);
                            siyuanshi[count].op2=(String)shuruliubeifen.elementAt(i+6);
                            siyuanshi[count].p1.result=(String)("t"+count);
                            count++;
                            siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+3);
                            siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                            siyuanshi[count].op2=(String)siyuanshi[count-1].p1.result;
                            siyuanshi[count].p1.result=(String)shuruliubeifen.elementAt(i);
                            count++;
                        }
                        else{
                            siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+3);
                            siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+2);
                            siyuanshi[count].op2=(String)shuruliubeifen.elementAt(i+4);
                            siyuanshi[count].p1.result="t"+count;
                            count++;
                            siyuanshi[count].fuhao=(String)shuruliubeifen.elementAt(i+5);
                            siyuanshi[count].op1=(String)shuruliubeifen.elementAt(i+6);
                            siyuanshi[count].op2=siyuanshi[count-1].p1.result;
                            siyuanshi[count].p1.result=(String)shuruliubeifen.elementAt(i);
                            count++;
                        }
                        i++;
                    }
                }
                else if(((String)shuruliubeifen.elementAt(i)).equals("}")&&(while_endsign!=0)) {
                    siyuanshi[count].fuhao="j";

                    siyuanshi[count].op1="_";
                    siyuanshi[count].op2="_";

                    huibianjump[sign]=while_beginsign;
                    sign++;
                    siyuanshi[count].p1.Address_Num=while_beginsign;
                    count++;
                    huibianjump[sign]=count;
                    sign++;
                    siyuanshi[while_endsign].p1.Address_Num=count;
                    while_endsign=0;
                    i++;
                }
                else
                    i++;
            }
        }
    }
    /** 判断是否出错 */
    public String IsError(Integer filenameid) throws Exception{
        String filename=String.valueOf(filenameid);
        String url="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/algorithm/service/codeFiles/"+filename+"synaxerror.txt";
        File errorlength = new File(url);
        if(errorlength.length()==0)
            return null;
        else{
            String errorInfo="",errorInfo1;
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(errorlength));
            BufferedReader bf1 = new BufferedReader(inputStreamReader);
            while ((errorInfo1 = bf1.readLine()) != null){
                errorInfo+=errorInfo1+"\n";
            }
            return errorInfo;
        }

    }
}
