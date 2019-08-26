package com.lin.cms.demo.sleeve.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.bo.SpecKeyAndItems;
import com.lin.cms.demo.sleeve.dto.SpecKeyCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.SkuMapper;
import com.lin.cms.demo.sleeve.mapper.SkuSpecMapper;
import com.lin.cms.demo.sleeve.mapper.SpecKeyMapper;
import com.lin.cms.demo.sleeve.mapper.SpecValueMapper;
import com.lin.cms.demo.sleeve.model.*;
import com.lin.cms.demo.sleeve.service.ISpecKeyService;
import com.lin.cms.exception.Forbidden;
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
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKey> implements ISpecKeyService {

    @Autowired
    private SpecValueMapper specValueMapper;

    @Autowired
    private SpecKeyMapper specKeyMapper;

    @Autowired
    private SkuSpecMapper skuSpecMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public void createSpecKey(SpecKeyCreateOrUpdateDTO dto) {
        // 不可创建同名规格key
        QueryWrapper<SpecKey> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpecKey::getName, dto.getName());
        SpecKey exist = this.getOne(wrapper);
        if (exist != null) {
            throw new Forbidden("已存在规格键，不能创建同名Key");
        }
        SpecKey specKey = new SpecKey();
        BeanUtils.copyProperties(dto, specKey);
        this.save(specKey);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void updateSpecKey(SpecKeyCreateOrUpdateDTO dto, Long id) {
        SpecKey exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格键");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);

        // 得到sku相关的
        List<Long> skuIds = skuSpecMapper.getSkuIdsByKeyId(id);
        skuIds.forEach(skuId -> {
            Sku sku = skuMapper.selectById(skuId);
            QueryWrapper<SkuSpec> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SkuSpec::getKeyId, id);
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
    public void deleteSpecKey(Long id) {
        SpecKey exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格键");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<SpecKey> getSpecKeyByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<SpecKey> iPage = this.getBaseMapper().selectPage(pager, null);
        List<SpecKey> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories, page, count);
    }

    @Override
    public SpecKeyAndItems getKeyAndValuesById(Long id) {
        SpecKey exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格键");
        }
        QueryWrapper<SpecValue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpecValue::getSpecId, exist.getId());
        List<SpecValue> items = specValueMapper.selectList(wrapper);
        SpecKeyAndItems specKeyAndItems = new SpecKeyAndItems(exist, items);
        return specKeyAndItems;
    }

    @Override
    public List<SpecKeySuggestionDO> getSuggestions(Long id, Long spuId) {
        return specKeyMapper.getSuggestions(id, spuId);
    }
}
