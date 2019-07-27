package com.lin.cms.demo.sleeve.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @author pedro
 * @since 2019-07-27
 */
public class SkuSpec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long skuId;

    private Long specKeyId;

    private Long specValueId;

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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSpecKeyId() {
        return specKeyId;
    }

    public void setSpecKeyId(Long specKeyId) {
        this.specKeyId = specKeyId;
    }

    public Long getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(Long specValueId) {
        this.specValueId = specValueId;
    }

    @Override
    public String toString() {
        return "SkuSpec{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", skuId=" + skuId +
                ", specKeyId=" + specKeyId +
                ", specValueId=" + specValueId +
                "}";
    }
}
