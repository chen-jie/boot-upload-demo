package com.maxrumo.upload.controller;

import com.maxrumo.upload.controller.service.QiniuService;
import com.maxrumo.upload.controller.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {

    @Autowired
    UploadService uploadService;
    @Autowired
    QiniuService qiniuService;

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String upload() {
        return "index";
    }

    @RequestMapping(value = {"normal"}, method = RequestMethod.GET)
    public String normal() {
        return "normal";
    }
    @RequestMapping(value = {"qiniu"}, method = RequestMethod.GET)
    public String qiniu() {
        return "qiniu";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        try {
            uploadService.upload(files);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }
    @RequestMapping(value = "/qupload", method = RequestMethod.POST)
    public @ResponseBody
    String qiniuUpload(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String msg = null;
        try {
            msg = qiniuService.upload(file);
        } catch (Exception e) {
            return e.getMessage();
        }
        return msg;
    }

    @RequestMapping(value = "/getUpToken")
    @ResponseBody
    public Map getUpToken(HttpServletRequest request) {
        return qiniuService.getUpToken();
    }
}