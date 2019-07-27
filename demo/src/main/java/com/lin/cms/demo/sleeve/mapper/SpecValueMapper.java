package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SpecKeyAndValue;
import com.lin.cms.demo.sleeve.model.SpecValue;
import org.apache.ibatis.annotations.Param;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface SpecValueMapper extends BaseMapper<SpecValue> {
    SpecKeyAndValue getSpecKeyAndValueById(@Param("kId") Long kId, @Param("vId") Long vId);
}
