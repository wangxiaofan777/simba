package com.wxf.service;

import com.wxf.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 多文件保存
     *
     * @param files 文件
     */
    void saveMultiFiles(MultipartFile[] files);
}
