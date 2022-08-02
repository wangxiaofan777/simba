package com.wxf.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author WangMaoSong
 * @date 2022/6/16 15:13
 */
@Data
public class Chunk implements Serializable {

    private static final long serialVersionUID = 1L;

    /***
     * ID
     */
    private String id;

    /**
     * 文件块，从1开始
     */
    private String chunkNum;

    /**
     * 分片大小
     */
    private Long chunkSize;

    /**
     * 当前块大小
     */
    private Long currentChunkSize;

    /**
     * 文件标识
     */
    private String identifier;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 总块数
     */
    private Integer totalChunks;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件
     */
    @Transient
    private MultipartFile file;

}
