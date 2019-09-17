package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.ActivityCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.ActivityMapper;
import com.lin.cms.demo.sleeve.mapper.CouponMapper;
import com.lin.cms.demo.sleeve.model.Activity;
import com.lin.cms.demo.sleeve.model.ActivityDetailDO;
import com.lin.cms.demo.sleeve.model.Coupon;
import com.lin.cms.demo.sleeve.service.IActivityService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private CouponMapper couponMapper;

    @Transactional
    @Override
    public void createActivity(ActivityCreateOrUpdateDTO dto) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(dto, activity);
        this.save(activity);
        // 关联category和coupon

        dto.getCouponIds().forEach(couponId -> {
        });
    }

    @Transactional
    @Override
    public void updateActivity(ActivityCreateOrUpdateDTO dto, Long id) {
        Activity exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动");
        }
        // 找到要删除的，找到保留的
        if (dto.getCouponIds() != null) {
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
        return PageResult.genPageResult(iPage.getTotal(), categories, page, count);
    }

    @Override
    public ActivityDetailDO getDetailById(Long id) {
        Activity exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的活动");
        }
        List<Long> coupons = couponMapper.getCouponsByActivityId(id);
        ActivityDetailDO activityDetail = new ActivityDetailDO();
        activityDetail.setCouponIds(coupons);
        BeanUtils.copyProperties(exist, activityDetail);
        return activityDetail;
    }

    private void updateActivityCategory(List<Long> newCategoryIds, List<Long> oldCategoryIds, Long activityId) {
        List<Long> add = findAdd(newCategoryIds, oldCategoryIds);
        List<Long> del = findDel(newCategoryIds, oldCategoryIds);
        add.forEach(it -> {
        });
        del.forEach(it -> {
        });
    }


    private void updateActivityCoupon(List<Long> newCouponIds, List<Long> oldCouponIds, Long activityId) {
        List<Long> add = findAdd(newCouponIds, oldCouponIds);
        List<Long> del = findDel(newCouponIds, oldCouponIds);
        add.forEach(it -> {
        });
        del.forEach(it -> {
        });
    }

    private List<Long> findAdd(List<Long> newIds, List<Long> oldIds) {
        List<Long> add = new ArrayList<>();
        newIds.forEach(newId -> {
            if (!oldIds.contains(newId)) {
                add.add(newId);
            }
        });
        return add;
    }

    private List<Long> findDel(List<Long> newIds, List<Long> oldIds) {
        List<Long> del = new ArrayList<>();
        oldIds.forEach(oldId -> {
            if (!newIds.contains(oldId)) {
                del.add(oldId);
            }
        });
        return del;
    }
}
