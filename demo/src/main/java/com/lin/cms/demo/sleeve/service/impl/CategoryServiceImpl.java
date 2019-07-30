package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.CategoryMapper;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.model.CategorySuggestionDO;
import com.lin.cms.demo.sleeve.service.ICategoryService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryCreateOrUpdateDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        this.save(category);
    }

    @Override
    public void updateCategory(CategoryCreateOrUpdateDTO dto, Long id) {
        Category exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的spu");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteCategory(Long id) {
        Category exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的spu");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Category> getCategoryByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Category> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Category> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }

    @Override
    public List<CategorySuggestionDO> getSuggestions(Long id) {
        return categoryMapper.getSuggestions(id);
    }
}
