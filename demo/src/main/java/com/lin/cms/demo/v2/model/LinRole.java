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
public class LinRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称，例如：搬砖者
     */
    private String name;

    /**
     * 角色路径，例如：/root/boy
     */
    private String path;

    /**
     * 角色登记，例如：2，root用户默认为1级，其它的依次向下递增
     */
    private Integer level;

    /**
     * 角色信息：例如：搬砖的人
     */
    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "LinRole{" +
            "id=" + id +
            ", name=" + name +
            ", path=" + path +
            ", level=" + level +
            ", info=" + info +
        "}";
    }
}
