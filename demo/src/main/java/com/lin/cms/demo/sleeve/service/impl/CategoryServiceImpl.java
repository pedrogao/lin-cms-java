package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.enums.CategoryRootOrNot;
import com.lin.cms.demo.sleeve.mapper.CategoryMapper;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.model.CategorySuggestionDO;
import com.lin.cms.demo.sleeve.service.ICategoryService;
import com.lin.cms.exception.Forbidden;
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
        //  如果当前为父分类
        if (exist.getIsRoot() == CategoryRootOrNot.ROOT.getValue()) {
            // 查找当前父分类下有无子分类，如有则不能删除
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getParentId, id);
            wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNot.NOT_ROOT.getValue());
            wrapper.last("limit 1");
            Category sub = this.getBaseMapper().selectOne(wrapper);
            if (sub != null) {
                throw new Forbidden("该分类下存在子分类，不可删除！");
            }
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Category> getCategoryByPage(Long count, Long page, Integer root) {
        Page pager = new Page(page, count);
        QueryWrapper<Category> wrapper;
        if (root == CategoryRootOrNot.ROOT.getValue()) {
            wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getIsRoot, root);
        } else {
            wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Category::getIsRoot, root);
        }
        IPage<Category> iPage = this.getBaseMapper().selectPage(pager, wrapper);
        List<Category> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }

    @Override
    public List<CategorySuggestionDO> getSuggestions(Long id) {
        return categoryMapper.getSuggestions(id);
    }

    @Override
    public PageResult<Category> getSubCategoryByPage(Long count, Long page, Integer id) {
        Page pager = new Page(page, count);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Category::getIsRoot, CategoryRootOrNot.NOT_ROOT.getValue());
        wrapper.lambda().eq(Category::getParentId, id);
        IPage<Category> iPage = this.getBaseMapper().selectPage(pager, wrapper);
        List<Category> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
