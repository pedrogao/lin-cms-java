package com.lin.cms.demo.sleeve.controller;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.sleeve.dto.BrandCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BrandMapper;
import com.lin.cms.demo.sleeve.model.Brand;
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
public class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandMapper brandMapper;

    private Long id;
    private String name = "千里之外";
    private String description = "千里之外，是周杰伦和费玉清一起发售的歌曲";

    @Before
    public void setUp() throws Exception {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setDescription(description);
        brandMapper.insert(brand);
        this.id = brand.getId();
    }

    @Test
    public void create() throws Exception {
        BrandCreateOrUpdateDTO validator = new BrandCreateOrUpdateDTO();
        validator.setName("安静");
        validator.setDescription(description);

        mvc.perform(post("/sleeve/brand/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("创建商品品牌成功！"));
    }

    @Test
    public void update() throws Exception {
        BrandCreateOrUpdateDTO validator = new BrandCreateOrUpdateDTO();
        String newName = "千里之外111";
        validator.setName(newName);
        validator.setDescription(description + "lllll");

        mvc.perform(put("/sleeve/brand/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新商品品牌成功！"));

        Brand brand = brandMapper.selectById(this.id);
        assertEquals(brand.getName(), newName);
        assertTrue(brand.getDescription().equals(description + "lllll"));
    }

    @Test
    public void deleteBrand() throws Exception {
        mvc.perform(delete("/sleeve/brand/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除商品品牌成功！"));
    }

    @Test
    public void getBrand() throws Exception {
        mvc.perform(get("/sleeve/brand/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.name").value(name));
    }

    @Test
    public void page() throws Exception {
        mvc.perform(get("/sleeve/brand/page")
                .param("page", "0")
                .param("count", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());
    }
}