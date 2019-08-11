package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.SpuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.SpuKeyAddDTO;
import com.lin.cms.demo.sleeve.mapper.*;
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

    @Autowired
    private SpuTagMapper spuTagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SpuImgMapper spuImgMapper;

    @Autowired
    private SpuDetailImgMapper spuDetailImgMapper;

    @SuppressWarnings("Duplicates")
    @Transactional
    @Override
    public void createSpu(SpuCreateOrUpdateDTO dto) {
        Spu spu = new Spu();
        BeanUtils.copyProperties(dto, spu);
        // 插入 root_category_id，for_theme_img
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category.getParentId() != null) {
            spu.setRootCategoryId(category.getParentId());
        }
        // 如果没有上传主图，则将banner的第一张图当作主图
        if (dto.getImg() == null && dto.getBannerImgs().size() > 0) {
            spu.setImg(dto.getBannerImgs().get(0));
        }
        this.save(spu);
        // 插入 banner_imgs,detail_imgs
        for (int i = 0; i < dto.getBannerImgs().size(); i++) {
            SpuImg spuImg = new SpuImg();
            spuImg.setImg(dto.getBannerImgs().get(i));
            spuImg.setSpuId(spu.getId());
            // spuImg.setThemeId();
            spuImgMapper.insert(spuImg);
        }
        for (int i = 0; i < dto.getDetailImgs().size(); i++) {
            SpuDetailImg spuDetailImg = new SpuDetailImg();
            spuDetailImg.setImg(dto.getDetailImgs().get(i));
            spuDetailImg.setSpuId(spu.getId());
            spuDetailImg.setIndex(i + 1);
            spuDetailImgMapper.insert(spuDetailImg);
        }
        createTags(dto.getTags(), spu.getId());
        SpuKeyAddDTO spuKeyAddDTO = new SpuKeyAddDTO();
        spuKeyAddDTO.setSpuId(spu.getId());
        spuKeyAddDTO.setSpecKeyIds(dto.getSpecKeyIds());
        this.addSpecKeyWithoutConfirm(spuKeyAddDTO);
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    @Override
    public void updateSpu(SpuCreateOrUpdateDTO dto, Long id) {
        Spu exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的分类");
        }
        BeanUtils.copyProperties(dto, exist);
        // 插入 root_category_id，for_theme_img
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category.getParentId() != null) {
            exist.setRootCategoryId(category.getParentId());
        }
        // 如果没有上传主图，则将banner的第一张图当作主图
        if (dto.getImg() == null && dto.getBannerImgs().size() > 0) {
            exist.setImg(dto.getBannerImgs().get(0));
        }
        this.updateById(exist);
        // 删除原来的banner_imgs
        // QueryWrapper<SpuImg> delWrapper1 = new QueryWrapper<>();
        // delWrapper1.lambda().eq(SpuImg::getSpuId, exist.getId());
        // spuImgMapper.delete(delWrapper1);
        spuImgMapper.hardDeleteImgsBySpuId(exist.getId());
        // 插入 banner_imgs,detail_imgs
        for (int i = 0; i < dto.getBannerImgs().size(); i++) {
            SpuImg spuImg = new SpuImg();
            spuImg.setImg(dto.getBannerImgs().get(i));
            spuImg.setSpuId(exist.getId());
            // spuImg.setThemeId();
            spuImgMapper.insert(spuImg);
        }
        // 删除原来的 detail_imgs
        QueryWrapper<SpuDetailImg> delWrapper2 = new QueryWrapper<>();
        delWrapper2.lambda().eq(SpuDetailImg::getSpuId, exist.getId());
        spuDetailImgMapper.delete(delWrapper2);
        for (int i = 0; i < dto.getDetailImgs().size(); i++) {
            SpuDetailImg spuDetailImg = new SpuDetailImg();
            spuDetailImg.setImg(dto.getDetailImgs().get(i));
            spuDetailImg.setSpuId(exist.getId());
            spuDetailImg.setIndex(i + 1);
            spuDetailImgMapper.insert(spuDetailImg);
        }
        createTags(dto.getTags(), exist.getId());
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
        List<Spu> records = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), records);
    }

    @Override
    public SpuWithNamesDO getWithNames(Long id) {
        SpuWithNamesDO spu = this.baseMapper.getWithNames(id);
        List<String> bannerImgs = spuImgMapper.getImgsBySpuId(id);
        List<String> detailImgs = spuDetailImgMapper.getImgsBySpuId(id);
        spu.setBannerImgs(bannerImgs);
        spu.setDetailImgs(detailImgs);
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


    private void createTags(String tags, Long spuId) {
        if (tags == null) {
            return;
        }
        String[] parts = tags.split("\\$");
        for (String part : parts) {
            QueryWrapper<Tag> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Tag::getTitle, part);
            Tag tag = tagMapper.selectOne(wrapper);
            if (tag == null) {
                tag = new Tag();
                tag.setTitle(part);
                tagMapper.insert(tag);

                SpuTag spuTag = new SpuTag();
                spuTag.setSpuId(spuId);
                spuTag.setTagId(tag.getId());
                spuTagMapper.insert(spuTag);
            } else {
                QueryWrapper<SpuTag> spuTagWrapper = new QueryWrapper<>();
                spuTagWrapper.lambda().eq(SpuTag::getSpuId, spuId);
                spuTagWrapper.lambda().eq(SpuTag::getTagId, tag.getId());
                SpuTag spuTag = spuTagMapper.selectOne(spuTagWrapper);
                if (spuTag == null) {
                    spuTag = new SpuTag();
                    spuTag.setSpuId(spuId);
                    spuTag.setTagId(tag.getId());
                    spuTagMapper.insert(spuTag);
                }
            }
        }
    }
}
