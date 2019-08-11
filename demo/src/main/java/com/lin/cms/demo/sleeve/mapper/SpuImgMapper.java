package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SpuImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface SpuImgMapper extends BaseMapper<SpuImg> {

    List<String> getImgsBySpuId(@Param("spuId") Long spuId);

    void hardDeleteImgsBySpuId(@Param("spuId") Long spuId);
}
