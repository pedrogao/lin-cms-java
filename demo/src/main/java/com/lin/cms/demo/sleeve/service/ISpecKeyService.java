package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.bo.SpecKeyAndItems;
import com.lin.cms.demo.sleeve.dto.SpecKeyCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.SpecKey;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface ISpecKeyService extends IService<SpecKey> {

    void createSpecKey(SpecKeyCreateOrUpdateDTO dto);

    void updateSpecKey(SpecKeyCreateOrUpdateDTO dto, Long id);

    void deleteSpecKey(Long id);

    PageResult<SpecKey> getSpecKeyByPage(Long count, Long page);

    SpecKeyAndItems getKeyAndValuesById(Long id);
}
