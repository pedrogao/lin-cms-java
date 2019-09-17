package com.lin.cms.demo.sleeve.model;


import lombok.Data;

import java.util.List;

@Data
public class SpuWithNamesDO {

    private Long id;

    private String title;

    private String subtitle;

    private Long categoryId;

    private String categoryName;

    private Integer online;

    // private Long brandId;

    private String brandName;

    private String tags;

    private String price;

    private String discountPrice;

    private Long sketchSpecId;

    private String sketchSpecName;

    private Long defaultSkuId;

    private String defaultSkuName;

    private String img;

    private Integer isTest;

    private Long rootCategoryId;

    // private String spuThemeImg;

    private String forThemeImg;

    private List<String> bannerImgs;

    private List<String> detailImgs;
}
