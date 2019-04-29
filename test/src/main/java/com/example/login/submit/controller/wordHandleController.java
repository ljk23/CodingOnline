package com.example.login.submit.controller;

import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.algorithm.service.SubmitWordMapper;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/submit")
public class wordHandleController {
    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private SubmitWordMapper submitWordMapper;

    @PostMapping("wordsubmit")
    public Boolean wordSubmit(@RequestBody Submitinfo submitinfo) throws IOException {
        /** 收到前端提交的代码 */

        /** 将代码写入文件 */
        if(submitWordMapper.writeWordIntoFile(submitinfo.getSubmitcontent())){
            System.out.println("写如文件成功");
            return true;
        }
        return true;
    }
}
