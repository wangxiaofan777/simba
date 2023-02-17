package com.wxf.service;

import com.wxf.model.Chunk;

/**
 * @author WangMaoSong
 * @date 2022/8/2 17:31
 */
public interface ChunkService {


    /**
     * 上传块文件
     *
     * @param chunk 块文件
     */
    void uploadChunk(Chunk chunk);


    /**
     * 检查是否已上传
     *
     * @param chunk 块文件
     * @return 是否已上传
     */
    boolean checkChunk(Chunk chunk);

}
