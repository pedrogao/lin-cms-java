package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.BrandCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Brand;
import com.lin.cms.demo.sleeve.model.BrandSuggestionDO;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IBrandService extends IService<Brand> {

    void createBrand(BrandCreateOrUpdateDTO dto);

    void updateBrand(BrandCreateOrUpdateDTO dto, Long id);

    void deleteBrand(Long id);

    PageResult<Brand> getBrandByPage(Long count, Long page);

    List<BrandSuggestionDO> getSuggestions(Long id);
}
