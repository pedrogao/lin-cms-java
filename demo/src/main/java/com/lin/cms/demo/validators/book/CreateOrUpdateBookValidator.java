package com.lin.cms.demo.validators.book;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CreateOrUpdateBookValidator {

    @NotEmpty(message = "必须传入图书名")
    private String title;

    @NotEmpty(message = "必须传入图书作者")
    private String author;

    @NotEmpty(message = "必须传入图书综述")
    private String summary;

    @Size(min = 0, max = 100, message = "图书插图的url长度必须在0~100之间")
    private String image;
}
