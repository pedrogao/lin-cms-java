package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SpuDetailImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface SpuDetailImgMapper extends BaseMapper<SpuDetailImg> {

    List<String> getImgsBySpuId(@Param("spuId") Long spuId);

    int hardDeleteImgsBySpuId(@Param("spuId") Long spuId);
}
