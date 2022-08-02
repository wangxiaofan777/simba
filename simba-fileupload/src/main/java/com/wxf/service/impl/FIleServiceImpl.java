package com.wxf.service.impl;

import com.wxf.model.Chunk;
import com.wxf.model.FileInfo;
import com.wxf.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 文件Service实现
 *
 * @author WangMaoSong
 * @date 2022/6/16 14:55
 */
@Service
public class FIleServiceImpl implements FileService {

    @Override
    public void mergerFile(FileInfo fileInfo) {

    }
}
