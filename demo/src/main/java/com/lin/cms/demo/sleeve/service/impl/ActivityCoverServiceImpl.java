package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.ActivityCoverCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.ActivityCoverMapper;
import com.lin.cms.demo.sleeve.model.ActivityCover;
import com.lin.cms.demo.sleeve.service.IActivityCoverService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityCoverServiceImpl extends ServiceImpl<ActivityCoverMapper, ActivityCover> implements IActivityCoverService {

    @Override
    public void createActivityCover(ActivityCoverCreateOrUpdateDTO dto) {
        ActivityCover activity = new ActivityCover();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
    }

    @Override
    public void updateActivityCover(ActivityCoverCreateOrUpdateDTO dto, Long id) {
        ActivityCover exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动页");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteActivityCover(Long id) {
        ActivityCover exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动页");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<ActivityCover> getActivityCoverByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<ActivityCover> iPage = this.getBaseMapper().selectPage(pager, null);
        List<ActivityCover> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
