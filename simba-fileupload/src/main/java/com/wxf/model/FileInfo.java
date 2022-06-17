package com.wxf.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @author WangMaoSong
 * @date 2022/6/16 15:09
 */
@Data
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 分片数
     */
    private Integer chunkNum;

    /**
     * 总文件大小
     */
    private long totalSize;

}
