package com.lin.cms.demo.sleeve.model;


import lombok.Data;

@Data
public class SpuWithNamesDO {

    private Long id;

    private String title;

    private String subtitle;

    private Long categoryId;

    private String categoryName;

    private Integer onSale;

    private Long brandId;

    private String brandName;

    private String tags;

    private String price;

    private Long sketchSpecId;

    private String sketchSpecName;

    private Long defaultSkuId;

    private String defaultSkuName;

    private String img;
}
