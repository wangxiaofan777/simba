package com.wxf.service;

import com.wxf.model.Chunk;
import com.wxf.model.FileInfo;

/**
 * 文件Service
 *
 * @author WangMaoSong
 * @date 2022/6/16 14:54
 */
public interface FileService {

    /**
     * 合并文件
     *
     * @param fileInfo 文件信息
     */
    void mergerFile(FileInfo fileInfo);
}
