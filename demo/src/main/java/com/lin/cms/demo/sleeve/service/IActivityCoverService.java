package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.ActivityCoverCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.ActivityCover;

public interface IActivityCoverService extends IService<ActivityCover> {
    void createActivityCover(ActivityCoverCreateOrUpdateDTO dto);

    void updateActivityCover(ActivityCoverCreateOrUpdateDTO dto, Long id);

    void deleteActivityCover(Long id);

    PageResult<ActivityCover> getActivityCoverByPage(Long count, Long page);
}
