package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.SkuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Sku;
import com.lin.cms.demo.sleeve.model.SkuWithNameDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface ISkuService extends IService<Sku> {

    void createSku(SkuCreateOrUpdateDTO dto);

    void updateSku(SkuCreateOrUpdateDTO dto, Long id);

    void deleteSku(Long id);

    PageResult<Sku> getSkuByPage(Long count, Long page);

    Sku getDetailById(Long id);

    List<SuggestionDO> getSuggestions(Long id, Long spuId);

    SkuWithNameDO getWithName(Long id);
}
