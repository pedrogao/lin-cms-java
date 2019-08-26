package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.BrandCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BrandMapper;
import com.lin.cms.demo.sleeve.model.Brand;
import com.lin.cms.demo.sleeve.model.BrandSuggestionDO;
import com.lin.cms.demo.sleeve.service.IBrandService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public void createBrand(BrandCreateOrUpdateDTO dto) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(dto, brand);
        this.save(brand);
    }

    @Override
    public void updateBrand(BrandCreateOrUpdateDTO dto, Long id) {
        Brand exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Brand> getBrandByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Brand> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Brand> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories, page, count);
    }

    @Override
    public List<BrandSuggestionDO> getSuggestions(Long id) {
        return brandMapper.getSuggestions(id);
    }
}
