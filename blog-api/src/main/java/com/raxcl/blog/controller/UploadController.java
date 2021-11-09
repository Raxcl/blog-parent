package com.raxcl.blog.controller;

import com.raxcl.blog.utils.QiniuUtils;
import com.raxcl.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    private final QiniuUtils qiniuUtils;

    public UploadController(QiniuUtils qiniuUtils) {
        this.qiniuUtils = qiniuUtils;
    }

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //原始文件名称  比如 aa.png
        String originalFilename = file.getOriginalFilename();
        //唯一的文件名称
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename,".");
        //上传文件，上传到哪儿呢？ 七牛云 云服务器 按量付费 速度快 把图片发放到离用户最近的服务器上
        // 降低我们自身应用服务器的带宽消耗
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(QiniuUtils.url+ fileName);
        }
        return Result.fail(20001, "上传失败");
    }
}
