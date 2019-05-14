package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CommonHandle implements CommonMapper {

    private CommonHandle commonHandle;

    public String readFile(String filepath,String value) throws IOException{
        try {
            File file = new File(filepath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    value+=lineTxt;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
            return null;
        }
              return value;
    }

    public void reWriteFile(String filepath,String value){
        /**  重定向输出到lll文件中 */
        try{
            PrintStream input=new PrintStream(new FileOutputStream(filepath));
            System.setOut(input);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(value);
    }
}
