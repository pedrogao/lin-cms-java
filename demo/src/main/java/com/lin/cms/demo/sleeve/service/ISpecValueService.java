package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.SpecValueCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.SpecValue;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface ISpecValueService extends IService<SpecValue> {

    void createSpecValue(SpecValueCreateOrUpdateDTO dto);

    void updateSpecValue(SpecValueCreateOrUpdateDTO dto, Long id);

    void deleteSpecValue(Long id);

    PageResult<SpecValue> getSpecValueByPage(Long count, Long page);
}
