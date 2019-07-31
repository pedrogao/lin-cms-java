package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.Sku;
import com.lin.cms.demo.sleeve.model.SkuWithNameDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface SkuMapper extends BaseMapper<Sku> {

    List<SuggestionDO> getSuggestions(@Param("id") Long id, @Param("spuId") Long spuId);

    SkuWithNameDO getWithName(@Param("id") Long id);

    Long getSpecValueId(@Param("keyId") Long keyId, @Param("skuId") Long skuId);
}
