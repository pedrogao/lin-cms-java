package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.demo.sleeve.model.ThirdUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/user")
public class ThirdUserController {
    @GetMapping
    public ThirdUser index() {
        ThirdUser user = new ThirdUser();
        user.setNickname("pedro");
        user.setEmail("123333@qq.com");
        user.setMobile("15827426475");
        return user;
    }
}
