package com.example.login.submit.controller;

import com.example.login.algorithm.service.Impl.SubmitWordHandle;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import com.example.login.user.dao.entity.User;
import com.example.login.user.dao.entity.Userinfo;
import com.example.login.user.dao.mapper.UserinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/getsubmit")
public class submitController {

    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private SubmitWordHandle submitWordHandle;

    @GetMapping(value = "/queryRankInfo")
    public List<Userinfo> queryAllUsers(){
        List<Userinfo> userinfos=userinfoMapper.selectAllUsers();
        if(userinfos.size()>0)
            return userinfos;
        return null;
    }

    @GetMapping(value ="/queryRank")
    public List<Submitinfo> queryRank(){
        List<Submitinfo> submitinfos=submitinfoMapper.queryRankUsers();
        if(submitinfos.size()>0)
            return submitinfos;
        return null;
    }


    @PostMapping(value = "/wordsubmit")
    public Integer uploadCode(@RequestParam("file") MultipartFile file) throws IOException {

        /**  判断上传的是单个文件还是文件夹 */
        String fileName=file.getOriginalFilename();
        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        System.out.println(fileTyle);

        /**  上传到指定目录下,并返回路径 */
        String filepath=submitWordHandle.uploadWordCode(file);
        System.out.println(file.getOriginalFilename());

        /**  单文件处理 */
        if(fileTyle.equals("java"))
        {
                /**  第一步-----进行编译-----   */
            if(submitWordHandle.Compiler(filepath)){
                System.out.println("编译成功");
                /** 第二步-----用例测试----  */
                /** first ---通过 problemId 拿到输入测试集-----  **/
                if(submitWordHandle.Answer(1,file)){
                    System.out.println("答案正确");
                    return 1;
                } else{
                    System.out.println("用例测试不通过");
                    return 2;
                }
            }else{
                System.out.println("编译失败");
                return 3;
            }
        }  /**  多文件处理 */
        else if(fileTyle.equals("zip")){
            /**  第一步---------解压缩----------   */
            String zipPath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+file.getOriginalFilename();
            String outPath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/";
            submitWordHandle.UnZip(zipPath,outPath);
            return 1;
        }
        else{
            return 4;
        }
        /**  创建线程池,将提交的任务加入队列 */
    }
}
