package com.lin.cms.demo.plugins.poem.app;

import com.github.pagehelper.PageHelper;
import com.lin.cms.core.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plugin/poem/")
public class PoemApi {

    @Value("${lin.cms.poem.limit}")
    private Integer limit;

    @Autowired
    private PoemMapper poemMapper;

    @GetMapping
    public PageResult index() {
        PageHelper.startPage(1, limit);
        List<PoemPO> poems = poemMapper.selectAll();
        return PageResult.genPageResult(limit, poems);
    }
}
