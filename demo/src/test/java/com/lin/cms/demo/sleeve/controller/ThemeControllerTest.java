package com.lin.cms.demo.sleeve.controller;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BannerMapper;
import com.lin.cms.demo.sleeve.mapper.ThemeMapper;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.Theme;
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
public class ThemeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ThemeMapper themeMapper;

    private Long id;
    private String title = "千里之外";
    private String description = "千里之外，是周杰伦和费玉清一起发售的歌曲";
    private String location = "top";
    private String img = "http://localhost/assets/11.png";

    @Before
    public void setUp() throws Exception {
        Theme theme = new Theme();
        theme.setTitle(title);
        theme.setDescription(description);
        theme.setLocation(location);
        theme.setImg(img);
        themeMapper.insert(theme);
        this.id = theme.getId();
    }

    @Test
    public void create() throws Exception {
        ThemeCreateOrUpdateDTO validator = new ThemeCreateOrUpdateDTO();
        validator.setTitle("安静");
        validator.setDescription(description);
        validator.setLocation(location);
        validator.setImg(img);

        mvc.perform(post("/sleeve/theme/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("创建商品主题成功！"));
    }

    @Test
    public void update() throws Exception {
        ThemeCreateOrUpdateDTO validator = new ThemeCreateOrUpdateDTO();
        String newName = "千里之外111";
        validator.setTitle(newName);
        validator.setDescription(description + "lllll");
        validator.setLocation(location);
        validator.setImg(img);

        mvc.perform(put("/sleeve/theme/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新商品主题成功！"));

        Theme theme = themeMapper.selectById(this.id);
        assertEquals(theme.getTitle(), newName);
        assertTrue(theme.getDescription().equals(description + "lllll"));
    }

    @Test
    public void deleteTheme() throws Exception {
        mvc.perform(delete("/sleeve/theme/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除商品主题成功！"));
    }

    @Test
    public void getTheme() throws Exception {
        mvc.perform(get("/sleeve/theme/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.title").value(title));
    }

    @Test
    public void page() throws Exception {
        mvc.perform(get("/sleeve/theme/page")
                .param("page", "0")
                .param("count", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());
    }
}