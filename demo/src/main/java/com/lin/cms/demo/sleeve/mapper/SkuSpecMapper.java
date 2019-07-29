package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SkuSpec;
import org.apache.ibatis.annotations.Param;

/**
 * @author pedro
 * @since 2019-07-27
 */
public interface SkuSpecMapper extends BaseMapper<SkuSpec> {

    void deleteSpecs(@Param("spuId") Long spuId, @Param("skuId") Long skuId);
}
