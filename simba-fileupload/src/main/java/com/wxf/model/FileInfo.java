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
     * ID
     */
    private Long id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 标识符
     */
    private String identifier;

    /**
     * 总文件大小
     */
    private long totalSize;

    /**
     * 类型
     */
    private String type;

    /**
     * 存放位置
     */
    private String location;

}
