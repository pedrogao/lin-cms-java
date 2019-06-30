package com.lin.cms.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "lin_file")
public class FileDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;

    /**
     * 1 local，其他表示其他地方
     */
    private Byte type;

    private String name;

    private String extension;

    private Integer size;

    /**
     * 图片md5值，防止上传重复图片
     */
    private String md5;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取1 local，其他表示其他地方
     *
     * @return type - 1 local，其他表示其他地方
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置1 local，其他表示其他地方
     *
     * @param type 1 local，其他表示其他地方
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取图片md5值，防止上传重复图片
     *
     * @return md5 - 图片md5值，防止上传重复图片
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 设置图片md5值，防止上传重复图片
     *
     * @param md5 图片md5值，防止上传重复图片
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }
}