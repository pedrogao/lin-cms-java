package com.lin.cms.demo.sleeve.service.impl;

import com.lin.cms.demo.sleeve.dto.SkuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.SkuSelector;
import com.lin.cms.demo.sleeve.model.Sku;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
@Slf4j
public class SkuServiceImplTest {

    @Autowired
    private SkuServiceImpl skuService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createSku() {
        SkuCreateOrUpdateDTO dto = new SkuCreateOrUpdateDTO();
        String title = "真·大碗宽面";
        String img = "https://pic4.zhimg.com/70/v2-bbb6d9f288bfb90f4d86fdd395c8c80b.jpg";
        BigDecimal discount = BigDecimal.valueOf(1.0);
        Integer onSale = 1; // 可以改为 0
        Long spuId = 1L;
        BigDecimal price = BigDecimal.valueOf(99.8);
        String currency = "CNY";
        Integer stock = 99;
        //[
        // {"key": "颜色", "value": "深白色", "key_id": 1, "value_id": 3},
        // {"key": "图案", "value": "灌篮高手", "key_id": 3, "value_id": 10},
        // {"key": "尺码", "value": "中号", "key_id": 4, "value_id": 15}
        // ]
        List<SkuSelector> selectors = new ArrayList<>();
        SkuSelector skuSelector1 = new SkuSelector();
        skuSelector1.setKeyId(1L);
        skuSelector1.setValueId(3L);
        selectors.add(skuSelector1);
        SkuSelector skuSelector2 = new SkuSelector();
        skuSelector2.setKeyId(3L);
        skuSelector2.setValueId(10L);
        selectors.add(skuSelector2);
        //
        dto.setTitle(title);
        dto.setImg(img);
        dto.setDiscount(discount);
        dto.setOnSale(onSale);
        dto.setSpuId(spuId);
        dto.setPrice(price);
        dto.setCurrency(currency);
        dto.setStock(stock);
        dto.setSelectors(selectors);
        //
        skuService.createSku(dto);
    }

    @Test
    public void updateSku() {
    }

    @Test
    public void deleteSku() {
    }

    @Test
    public void getSkuByPage() {
    }

    @Test
    public void getDetailById() {
        Sku sku = skuService.getById(1);
        log.info(sku.toString());
        assertNotNull(sku);
    }
}