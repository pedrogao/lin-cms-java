package com.lin.cms.demo.sleeve.controller;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.enums.CategoryRootOrNot;
import com.lin.cms.demo.sleeve.mapper.BannerMapper;
import com.lin.cms.demo.sleeve.mapper.CategoryMapper;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class BannerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BannerMapper bannerMapper;

    private Long id;
    private String name = "千里之外";
    private String description = "千里之外，是周杰伦和费玉清一起发售的歌曲";

    @Before
    public void setUp() throws Exception {
        Banner banner = new Banner();
        banner.setName(name);
        banner.setDescription(description);
        bannerMapper.insert(banner);
        this.id = banner.getId();
    }

    @Test
    public void create() throws Exception {
        BannerCreateOrUpdateDTO validator = new BannerCreateOrUpdateDTO();
        validator.setName("安静");
        validator.setDescription(description);

        mvc.perform(post("/sleeve/banner/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("创建商品banner成功！"));
    }

    @Test
    public void update() throws Exception {
        BannerCreateOrUpdateDTO validator = new BannerCreateOrUpdateDTO();
        String newName = "千里之外111";
        validator.setName(newName);
        validator.setDescription(description + "lllll");

        mvc.perform(put("/sleeve/banner/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新商品banner成功！"));

        Banner banner = bannerMapper.selectById(this.id);
        assertEquals(banner.getName(), newName);
        assertTrue(banner.getDescription().equals(description + "lllll"));
    }

    @Test
    public void deleteBanner() throws Exception {
        mvc.perform(delete("/sleeve/banner/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除商品banner成功！"));
    }

    @Test
    public void getBanner() throws Exception {
        mvc.perform(get("/sleeve/banner/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.name").value(name));
    }

    @Test
    public void page() throws Exception {
        mvc.perform(get("/sleeve/banner/page")
                .param("page", "0")
                .param("count", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());
    }
}