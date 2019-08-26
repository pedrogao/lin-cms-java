package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.demo.sleeve.dto.GridCategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.CategoryMapper;
import com.lin.cms.demo.sleeve.mapper.GridCategoryMapper;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.model.GridCategory;
import com.lin.cms.demo.sleeve.service.IGridCategoryService;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
@Service
public class GridCategoryServiceImpl extends ServiceImpl<GridCategoryMapper, GridCategory> implements IGridCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private static int numLimit = 6;

    @Override
    public void createGridCategory(GridCategoryCreateOrUpdateDTO dto) {
        Integer count = this.getBaseMapper().selectCount(null);
        if (count >= numLimit) {
            throw new Forbidden("六宫格数量不能超过六个，不可创建");
        }
        Category category = categoryMapper.getById(dto.getCategoryId());
        if (category == null) {
            throw new NotFound("未找到相关的分类");
        }
        GridCategory gridCategory = new GridCategory();
        setGridCategoryByCondition(dto, gridCategory, category);
        this.save(gridCategory);
    }

    @Override
    public void updateGridCategory(GridCategoryCreateOrUpdateDTO dto, Long id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFound("未找到相关的宫格");
        }
        Category category = categoryMapper.getById(dto.getCategoryId());
        if (category == null) {
            throw new NotFound("未找到相关的分类");
        }
        setGridCategoryByCondition(dto, gridCategory, category);
        this.updateById(gridCategory);
    }


    private void setGridCategoryByCondition(GridCategoryCreateOrUpdateDTO dto, GridCategory gridCategory, Category category) {
        // 如果存在 title，赋值 title，否则填充 name
        if (dto.getTitle() == null) {
            gridCategory.setTitle(category.getName());
        } else {
            gridCategory.setTitle(dto.getTitle());
        }
        if (dto.getName() != null) {
            gridCategory.setName(dto.getName());
        } else {
            gridCategory.setName(category.getName());
        }
        gridCategory.setImg(dto.getImg());
        // 如果当前绑定的分类无父分类，则绑定到rootCategoryId
        // 否则绑定父分类绑定到rootCategoryId，当前id绑定到categoryId
        if (category.getParentId() == null) {
            gridCategory.setRootCategoryId(category.getId());
        } else {
            gridCategory.setRootCategoryId(category.getParentId());
            gridCategory.setCategoryId(category.getId());
        }
    }

    @Override
    public List<GridCategory> getList() {
        return this.list();
    }

    @Override
    public void deleteGridCategory(Long id) {
        GridCategory gridCategory = this.getById(id);
        if (gridCategory == null) {
            throw new NotFound("未找到相关的宫格");
        }
        this.getBaseMapper().deleteById(id);
    }
}
