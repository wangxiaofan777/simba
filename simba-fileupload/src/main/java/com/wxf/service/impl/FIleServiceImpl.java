package com.wxf.service.impl;

import com.wxf.model.FileInfo;
import com.wxf.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 文件Service实现
 *
 * @author WangMaoSong
 * @date 2022/6/16 14:55
 */
@Service
public class FIleServiceImpl implements FileService {

    @Value("${file.temp.dir:/tmp}")
    private String fileTempDir;

    @Override
    public void mergerFile(FileInfo fileInfo) {

    }

    @Override
    public void saveMultiFiles(MultipartFile[] files) {
        Arrays.stream(files)
                .forEach(file -> {
                    try {
                        file.transferTo(Paths.get(fileTempDir + "/" + file.getOriginalFilename()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }
}
