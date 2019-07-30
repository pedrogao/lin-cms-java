package com.lin.cms.demo.sleeve.model;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuWithNameDO {
    private Long id;

    private BigDecimal price;

    private BigDecimal discount;

    private Integer onSale;

    private String img;

    private String title;

    private String specs;

    private Long spuId;

    private String spuName;

    private String code;

    private String currency;

    private Integer stock;
}
