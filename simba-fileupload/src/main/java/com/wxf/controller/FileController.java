package com.wxf.controller;

import com.wxf.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传Controller
 *
 * @author WangMaoSong
 * @date 2022/6/16 14:54
 */
@RestController
public class FileController {

    private final IFileService fileService;

    @Autowired
    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public void upload() {

    }
}
