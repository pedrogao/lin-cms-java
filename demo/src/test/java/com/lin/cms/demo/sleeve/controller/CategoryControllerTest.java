package com.lin.cms.demo.sleeve.controller;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.enums.CategoryRootOrNot;
import com.lin.cms.demo.sleeve.mapper.CategoryMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CategoryMapper categoryMapper;

    private Long id;
    private String name = "千里之外";
    private String description = "千里之外，是周杰伦和费玉清一起发售的歌曲";
    private Integer isRoot = CategoryRootOrNot.ROOT.getValue();
    private Long parentId = 21L;

    @Before
    public void setUp() {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setIsRoot(isRoot);
        category.setParentId(parentId);
        categoryMapper.insert(category);
        this.id = category.getId();
    }

    @Test
    public void create() throws Exception {
        CategoryCreateOrUpdateDTO validator = new CategoryCreateOrUpdateDTO();
        validator.setName("安静");
        validator.setDescription(description);

        mvc.perform(post("/sleeve/category/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("创建商品种类成功！"));
    }

    @Test
    public void update() throws Exception {
        CategoryCreateOrUpdateDTO validator = new CategoryCreateOrUpdateDTO();
        String newName = "千里之外111";
        validator.setName(newName);
        validator.setDescription(description);
        validator.setParentId(22L);
        validator.setIsRoot(CategoryRootOrNot.NOT_ROOT.getValue());

        mvc.perform(put("/sleeve/category/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新商品种类成功！"));

        Category category = categoryMapper.selectById(this.id);
        assertEquals(category.getName(), newName);
        assertTrue(category.getIsRoot() == CategoryRootOrNot.NOT_ROOT.getValue());
        assertTrue(category.getParentId() == 22L);
    }

    @Test
    public void deleteCategory() throws Exception {
        mvc.perform(delete("/sleeve/category/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除商品种类成功！"));
    }

    @Test
    public void getCategory() throws Exception {
        mvc.perform(get("/sleeve/category/" + this.id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.name").value(name));
    }

    @Test
    public void getCategoryNotFound() throws Exception {
        mvc.perform(get("/sleeve/category/" + this.id + 100)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("未找到相关的分类"));
    }

    @Test
    public void page() throws Exception {
        mvc.perform(get("/sleeve/category/page")
                .param("page", "0")
                .param("count", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());
    }
}