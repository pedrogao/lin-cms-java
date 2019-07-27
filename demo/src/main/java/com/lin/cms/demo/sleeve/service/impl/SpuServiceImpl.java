package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.SpuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.SpuMapper;
import com.lin.cms.demo.sleeve.model.Spu;
import com.lin.cms.demo.sleeve.service.ISpuService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ISpuService {

    @Override
    public void createSpu(SpuCreateOrUpdateDTO dto) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(dto, spu);
        this.save(spu);
    }

    @Override
    public void updateSpu(SpuCreateOrUpdateDTO dto, Long id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteSpu(Long id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Spu> getSpuByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Spu> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Spu> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
