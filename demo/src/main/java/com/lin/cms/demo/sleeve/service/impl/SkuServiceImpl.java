package com.lin.cms.demo.sleeve.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.SkuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.SkuSelector;
import com.lin.cms.demo.sleeve.mapper.*;
import com.lin.cms.demo.sleeve.model.Sku;
import com.lin.cms.demo.sleeve.model.SkuSpec;
import com.lin.cms.demo.sleeve.model.SpecKeyAndValue;
import com.lin.cms.demo.sleeve.model.Spu;
import com.lin.cms.demo.sleeve.service.ISkuService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.exception.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {

    @Autowired
    private SpecKeyMapper specKeyMapper;

    @Autowired
    private SpecValueMapper specValueMapper;

    @Autowired
    private SkuSpecMapper skuSpecKeyValueMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Transactional
    @Override
    public void createSku(SkuCreateOrUpdateDTO dto) {
        // 1. 检测选择器是否存在
        // 检测 spu 是否存在
        Spu spu = spuMapper.selectById(dto.getSpuId());
        if (spu == null) {
            throw new NotFound("未找到相关的spu，请检查参数");
        }
        List<SkuSelector> selectors = dto.getSelectors();
        boolean ok = this.checkSelectors(selectors);
        if (!ok) {
            throw new Parameter("sku的规则值属性错误，请重新选择规则键值");
        }
        // 2. 存储sku基础信息
        Sku sku = new Sku();
        String code = this.generateSkuCode(selectors, dto.getSpuId());
        sku.setCode(code);
        sku.setCurrency(dto.getCurrency());
        sku.setDiscount(dto.getDiscount());
        sku.setImg(dto.getImg());
        sku.setOnSale(dto.getOnSale());
        sku.setSpuId(dto.getSpuId());
        sku.setPrice(dto.getPrice());
        sku.setStock(dto.getStock());
        sku.setTitle(dto.getTitle());
        String specs = JSON.toJSONString(selectors);
        sku.setSpecs(specs);
        this.save(sku);

        // 3. 存储信息到关联表中，防止规格更新后查询的信息错误
        // sku_spec_key_value
        selectors.forEach(selector -> {
            SkuSpec skuSpec = new SkuSpec();
            skuSpec.setSpuId(dto.getSpuId());
            skuSpec.setSkuId(sku.getId());
            skuSpec.setSpecKeyId(selector.getKeyId());
            skuSpec.setSpecValueId(selector.getValueId());
            skuSpecKeyValueMapper.insert(skuSpec);
        });
    }

    @Override
    public void updateSku(SkuCreateOrUpdateDTO dto, Long id) {

    }

    @Override
    public void deleteSku(Long id) {

    }

    @Override
    public PageResult<Sku> getSkuByPage(Long count, Long page) {
        return null;
    }

    @Override
    public Sku getDetailById(Long id) {
        // 查询关联关系
        return null;
    }

    private boolean checkSelectors(List<SkuSelector> selectors) {
        for (int i = 0; i < selectors.size(); i++) {
            SkuSelector selector = selectors.get(i);
            SpecKeyAndValue specKeyAndValue = specValueMapper.getSpecKeyAndValueById(selector.getKeyId(), selector.getValueId());
            if (!specKeyAndValue.getSpecKey().equals(selector.getKey()) ||
                    !specKeyAndValue.getSpecValue().equals(selector.getValue())) {
                return false;
            }
        }
        return true;
    }

    private String generateSkuCode(List<SkuSelector> selectors, Long spuId) {
        // 调整：sku的code 调整成$分隔spu和sku，#分隔sku片段
        selectors.sort((o1, o2) -> (int) (o1.getKeyId() - o2.getKeyId()));
        StringBuilder builder = new StringBuilder();
        builder.append(spuId);
        builder.append("$");
        selectors.forEach(skuSelector -> {
            builder.append(skuSelector.getKeyId());
            builder.append("-");
            builder.append(skuSelector.getValueId());
            builder.append("#");
        });
        return builder.toString();
    }
}
