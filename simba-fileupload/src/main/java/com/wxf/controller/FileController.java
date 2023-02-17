package com.wxf.controller;

import com.wxf.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传Controller
 *
 * @author WangMaoSong
 * @date 2022/6/16 14:54
 */
@RestController
@RequestMapping(value = "/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public void upload() {

    }

    @PostMapping(value = "/multi")
    public void multiUpload(@RequestParam("files") MultipartFile[] files) {
        this.fileService.saveMultiFiles(files);
    }
}
