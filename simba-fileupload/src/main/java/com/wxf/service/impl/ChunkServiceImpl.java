package com.wxf.service.impl;

import com.wxf.model.Chunk;
import com.wxf.service.ChunkService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 文件块Service
 *
 * @author WangMaoSong
 * @date 2022/8/2 17:31
 */
@Service
public class ChunkServiceImpl implements ChunkService {

    private String uploadFolder;

    @Override
    public void uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();

        try {
            file.transferTo(Paths.get(uploadFolder + "/" + chunk.getIdentifier()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkChunk(Chunk chunk) {
        return false;
    }
}
