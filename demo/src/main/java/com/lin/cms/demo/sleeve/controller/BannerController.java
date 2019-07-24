package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.demo.sleeve.model.Banner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/banner")
public class BannerController {

    @GetMapping
    public Banner index() {
        Banner banner = new Banner();
        banner.setName("bbbb");
        banner.setDescription("就是一个banner");
        return banner;
    }
}
