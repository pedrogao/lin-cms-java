package com.lin.cms.demo.sleeve.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActivityDetailDO {

    private Long id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer online;

    private String entranceImg;

    private String internalTopImg;

    private String name;

    private Long activityCoverId;

    private List<Long> couponIds;

    private List<Long> categoryIds;
}
