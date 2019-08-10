package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.demo.sleeve.dto.GridCategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.GridCategory;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface IGridCategoryService extends IService<GridCategory> {

    void createGridCategory(GridCategoryCreateOrUpdateDTO dto);

    void updateGridCategory(GridCategoryCreateOrUpdateDTO dto, Long id);

    List<GridCategory> getList();

    void deleteGridCategory(Long id);
}
