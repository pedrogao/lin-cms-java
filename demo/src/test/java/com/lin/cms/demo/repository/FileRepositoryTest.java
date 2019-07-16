package com.lin.cms.demo.repository;

import com.lin.cms.demo.mapper.FileMapper;
import com.lin.cms.demo.model.FileDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    private String md5 = "iiiiilllllll";
    private String name = "千里之外";

    @Before
    public void setUp() throws Exception {
        FileDO fileDO = new FileDO();
        fileDO.setName(name);
        fileDO.setPath("千里之外...");
        fileDO.setSize(1111);
        fileDO.setExtension(".png");
        fileDO.setMd5(md5);
        fileRepository.save(fileDO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findTopByMd5() {
        FileDO md5 = fileRepository.findTopByMd5(this.md5);
        assertEquals(md5.getName(), name);
    }
}