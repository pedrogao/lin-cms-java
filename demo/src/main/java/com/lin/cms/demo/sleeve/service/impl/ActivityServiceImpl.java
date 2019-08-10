package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.ActivityCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.ActivityMapper;
import com.lin.cms.demo.sleeve.model.Activity;
import com.lin.cms.demo.sleeve.service.IActivityService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Override
    public void createActivity(ActivityCreateOrUpdateDTO dto) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
    }

    @Override
    public void updateActivity(ActivityCreateOrUpdateDTO dto, Long id) {
        Activity exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteActivity(Long id) {
        Activity exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Activity> getActivityByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Activity> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Activity> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
