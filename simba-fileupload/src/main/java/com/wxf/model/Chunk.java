package com.wxf.model;

import lombok.Data;

/**
 * @author WangMaoSong
 * @date 2022/6/16 15:13
 */
@Data
public class Chunk {

    /**
     * 文件名
     */
    private String filename;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 分片名
     */
    private String uuid;

    /**
     * 分片大小
     */
    private Long chunkSize;
}
