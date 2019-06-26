package com.lin.cms.plugins.poem.app;

import com.github.pagehelper.PageHelper;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/search")
    public PageResult count(@RequestParam("author") String author) {
        // 纳兰性德
        List<PoemPO> poems = poemMapper.findPoemsByAuthor(author);
        // Integer count = poemMapper.getCount();
        return PageResult.genPageResult(2, poems);
    }
}
