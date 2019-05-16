package com.example.login.algorithm.service.Impl;

import com.example.login.algorithm.service.CommonMapper;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CommonHandle implements CommonMapper {

    @Autowired
    private CommonHandle commonHandle;

    @Autowired
    private SubmitinfoMapper submitinfoMapper;

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

    public List<String> getResult(Submitinfo submitinfo, Integer result,List<String> list,String value){
        if(result==1){   /**  成功通过 */
            submitinfo.setIssuccess(true);
            submitinfo.setSubmitsuccess(1);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(result));
        }else if(result==2){
            /** 用例不通过  */
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfo.setErrorcause("用例不通过");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(result));
            list.add("用例不通过");
        } else if(result==3) {
            /**  编译错误 */
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfo.setErrorcause(value);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(3));
            list.add(value);
        }else if(result==4){
            /** 超时  */
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfo.setErrorcause("超时");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(result));
            list.add("超时");
        }else if(result==5){
            /** 超过内存  */
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfo.setErrorcause("超过内存");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(result));
            list.add("超过内存");
        }
        else if(result==6){
            /** 格式错误  */
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfo.setErrorcause("格式错误");
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            list.add(String.valueOf(result));
            list.add("格式错误");
        }
        return list;
    }
}
