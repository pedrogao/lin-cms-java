package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.bo.SpecKeyAndItems;
import com.lin.cms.demo.sleeve.dto.SpecKeyCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.SpecKeyMapper;
import com.lin.cms.demo.sleeve.mapper.SpecValueMapper;
import com.lin.cms.demo.sleeve.model.SpecKey;
import com.lin.cms.demo.sleeve.model.SpecKeySuggestionDO;
import com.lin.cms.demo.sleeve.model.SpecValue;
import com.lin.cms.demo.sleeve.service.ISpecKeyService;
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
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKey> implements ISpecKeyService {

    @Autowired
    private SpecValueMapper specValueMapper;

    @Autowired
    private SpecKeyMapper specKeyMapper;

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

    @Override
    public void updateSpecKey(SpecKeyCreateOrUpdateDTO dto, Long id) {
        SpecKey exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格键");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
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
        return PageResult.genPageResult(iPage.getTotal(), categories);
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
    public List<SpecKeySuggestionDO> getSuggestions(Long id) {
        return specKeyMapper.getSuggestions(id);
    }
}
