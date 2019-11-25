package com.lin.cms.demo.v2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pedro
 * @since 2019-11-25
 */
public class LinFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String path;

    /**
     * 1 local，其他表示其他地方
     */
    private Integer type;

    private String name;

    private String extension;

    private Integer size;

    /**
     * md5值，防止上传重复文件
     */
    private String md5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "LinFile{" +
            "id=" + id +
            ", path=" + path +
            ", type=" + type +
            ", name=" + name +
            ", extension=" + extension +
            ", size=" + size +
            ", md5=" + md5 +
        "}";
    }
}
