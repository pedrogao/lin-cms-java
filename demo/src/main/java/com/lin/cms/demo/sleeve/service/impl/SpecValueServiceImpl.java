package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.SpecValueCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.SpecValueMapper;
import com.lin.cms.demo.sleeve.model.SpecValue;
import com.lin.cms.demo.sleeve.service.ISpecValueService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class SpecValueServiceImpl extends ServiceImpl<SpecValueMapper, SpecValue> implements ISpecValueService {

    @Override
    public void createSpecValue(SpecValueCreateOrUpdateDTO dto) {
        SpecValue specValue = new SpecValue();
        BeanUtils.copyProperties(dto, specValue);
        this.save(specValue);
    }

    @Override
    public void updateSpecValue(SpecValueCreateOrUpdateDTO dto, Long id) {
        SpecValue exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的规格值");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
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
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
