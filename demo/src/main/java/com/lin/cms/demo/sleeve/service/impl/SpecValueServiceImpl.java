package com.lin.cms.demo.sleeve.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.SpecValueCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.SkuMapper;
import com.lin.cms.demo.sleeve.mapper.SkuSpecMapper;
import com.lin.cms.demo.sleeve.mapper.SpecValueMapper;
import com.lin.cms.demo.sleeve.model.*;
import com.lin.cms.demo.sleeve.service.ISpecValueService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValue> implements ISpecValueService {

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Override
    public void createSpecValue(SpecValueCreateOrUpdateDTO dto) {
        SpecValue specValue = new SpecValue();
        BeanUtils.copyProperties(dto, specValue);
        this.save(specValue);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void updateSpecValue(SpecValueCreateOrUpdateDTO dto, Long id) {
        SpecValue exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格值");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);

        // 得到sku相关的
        List<Long> skuIds = skuSpecMapper.getSkuIdsByValueId(id);
        skuIds.forEach(skuId -> {
            Sku sku = skuMapper.selectById(skuId);
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getValueId, id);
            wrapper.lambda().eq(SkuSpec::getSkuId, skuId);
            List<SkuSpec> skuSpecs = skuSpecMapper.selectList(wrapper);
            List<SpecKeyAndValue> specs = new ArrayList<>();
            skuSpecs.forEach(skuSpec -> {
                SpecKeyAndValue specKeyAndValue = specValueMapper.getSpecKeyAndValueById(skuSpec.getKeyId(), skuSpec.getValueId());
                specs.add(specKeyAndValue);
            });
            String specsStr = JSON.toJSONString(specs);
            sku.setSpecs(specsStr);
            skuMapper.updateById(sku);
        });
    }

    @Override
    public void deleteSpecValue(Long id) {
        SpecValue exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格值");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<SpecValue> getSpecValueByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<SpecValue> iPage = this.getBaseMapper().selectPage(pager, null);
        List<SpecValue> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories, page, count);
    }

    @Override
    public List<SuggestionDO> getSuggestions(Long keyId) {
        return this.getBaseMapper().getSuggestions(keyId);
    }
}
