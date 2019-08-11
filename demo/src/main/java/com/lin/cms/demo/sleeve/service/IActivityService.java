package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.ActivityCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Activity;
import com.lin.cms.demo.sleeve.model.ActivityDetailDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pedro
 * @since 2019-08-09
 */
public interface IActivityService extends IService<Activity> {

    void createActivity(ActivityCreateOrUpdateDTO dto);

    void updateActivity(ActivityCreateOrUpdateDTO dto, Long id);

    void deleteActivity(Long id);

    PageResult<Activity> getActivityByPage(Long count, Long page);

    ActivityDetailDO getDetailById(Long id);
}
