package com.lin.cms.demo.sleeve.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author pedro
 * @since 2019-07-31
 */
public class SpuKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long specKeyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Long getSpecKeyId() {
        return specKeyId;
    }

    public void setSpecKeyId(Long specKeyId) {
        this.specKeyId = specKeyId;
    }

    @Override
    public String toString() {
        return "SpuKey{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", specKeyId=" + specKeyId +
                "}";
    }
}
