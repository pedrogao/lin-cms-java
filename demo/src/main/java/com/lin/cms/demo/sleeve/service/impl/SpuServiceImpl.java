package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.SpuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.SpuKeyAddDTO;
import com.lin.cms.demo.sleeve.mapper.SpuKeyMapper;
import com.lin.cms.demo.sleeve.mapper.SpuMapper;
import com.lin.cms.demo.sleeve.mapper.TagMapper;
import com.lin.cms.demo.sleeve.model.*;
import com.lin.cms.demo.sleeve.service.ISpuService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ISpuService {

    @Autowired
    private SpuKeyMapper spuKeyMapper;

    @Autowired
    private TagMapper tagMapper;

    private static String spuPosition = "spu";

    @Transactional
    @Override
    public void createSpu(SpuCreateOrUpdateDTO dto) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(dto, spu);
        this.save(spu);
        createTags(dto.getTags());
        SpuKeyAddDTO spuKeyAddDTO = new SpuKeyAddDTO();
        spuKeyAddDTO.setSpuId(spu.getId());
        spuKeyAddDTO.setSpecKeyIds(dto.getSpecKeyIds());
        this.addSpecKeyWithoutConfirm(spuKeyAddDTO);
    }

    @Transactional
    @Override
    public void updateSpu(SpuCreateOrUpdateDTO dto, Long id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
        createTags(dto.getTags());
        SpuKeyAddDTO spuKeyAddDTO = new SpuKeyAddDTO();
        spuKeyAddDTO.setSpuId(exist.getId());
        spuKeyAddDTO.setSpecKeyIds(dto.getSpecKeyIds());
        this.addSpecKeyWithoutConfirm(spuKeyAddDTO);
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

    @Override
    public SpuWithNamesDO getWithNames(Long id) {
        SpuWithNamesDO spu = this.baseMapper.getWithNames(id);
        return spu;
    }

    @Override
    public List<SuggestionDO> getSuggestions(Long id) {
        return this.baseMapper.getSuggestions(id);
    }

    @Transactional
    @Override
    public void addSpecKey(SpuKeyAddDTO dto) {
        Spu spu = this.getBaseMapper().selectById(dto.getSpuId());
        if (spu == null) {
            throw new NotFound("未找到相关的spu");
        }
        addSpecKeyWithoutConfirm(dto);
    }

    @Override
    public List<Long> getSpecKeys(Long id) {
        return this.getBaseMapper().getSpecKeys(id);
    }

    private void addSpecKeyWithoutConfirm(SpuKeyAddDTO dto) {
        // 先找到原来的 spu_key 删除后
        Long spuId = dto.getSpuId();
        QueryWrapper<SpuKey> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SpuKey::getSpuId, spuId);
        List<SpuKey> exists = spuKeyMapper.selectList(wrapper);
        // 找到新增的，添加
        // 找到删除的，删除
        findAddKeys(exists, dto.getSpecKeyIds(), spuId);
        findDeleteKeys(exists, dto.getSpecKeyIds(), spuId);
    }

    private void findAddKeys(List<SpuKey> exists, List<Long> specKeyIds, Long spuId) {
        List<Long> addSpecKeys = new ArrayList<>();
        specKeyIds.forEach(specKeyId -> {
            // 如果没有在exists中存在的，则添加
            boolean contains = this.contain(exists, specKeyId);
            if (!contains) {
                addSpecKeys.add(specKeyId);
            }
        });
        addSpecKeys.forEach(addSpecKey -> {
            SpuKey spuKey = new SpuKey();
            spuKey.setSpuId(spuId);
            spuKey.setSpecKeyId(addSpecKey);
            spuKeyMapper.insert(spuKey);
        });
    }


    private boolean contain(List<SpuKey> exists, Long specKeyId) {
        for (SpuKey exist : exists) {
            if (exist.getSpecKeyId().equals(specKeyId)) {
                return true;
            }
        }
        return false;
    }


    private void findDeleteKeys(List<SpuKey> exists, List<Long> specKeyIds, Long spuId) {
        List<Long> deleteSpecKeys = new ArrayList<>();
        exists.forEach(exist -> {
            boolean contains = specKeyIds.contains(exist.getSpecKeyId());
            if (!contains) {
                deleteSpecKeys.add(exist.getSpecKeyId());
            }
        });
        deleteSpecKeys.forEach(deleteSpecKey -> {
            QueryWrapper<SpuKey> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SpuKey::getSpuId, spuId);
            wrapper.lambda().eq(SpuKey::getSpecKeyId, deleteSpecKey);
            spuKeyMapper.delete(wrapper);
        });
    }


    private void createTags(String tags) {
        if (tags == null) {
            return;
        }
        String[] parts = tags.split("\\$");
        for (String part : parts) {
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Tag::getPosition, spuPosition);
            wrapper.lambda().eq(Tag::getName, part);
            Tag tag = tagMapper.selectOne(wrapper);
            if (tag == null) {
                tag = new Tag();
                tag.setName(part);
                tag.setPosition(spuPosition);
                tagMapper.insert(tag);
            }
        }
    }
}
