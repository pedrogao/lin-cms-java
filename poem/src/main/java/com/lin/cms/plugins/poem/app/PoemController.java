package com.lin.cms.plugins.poem.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.cms.response.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plugin/poem/")
public class PoemController {

    @Value("${lin.cms.poem.limit}")
    private Integer limit;

    @Autowired
    private PoemMapper poemMapper;

    @GetMapping
    public PageResult index() {
        long pageNum = 1L;
        Page<PoemDO> pager = new Page<>(pageNum, limit);
        IPage<PoemDO> page = poemMapper.selectPage(pager, null);
        return PageResult.genPageResult(page.getTotal(), page.getRecords(), pageNum, limit);
    }

    @GetMapping("/search")
    public PageResult count(@RequestParam("author") String author) {
        // 纳兰性德
        List<PoemDO> poems = poemMapper.findPoemsByAuthor(author);
        return PageResult.genPageResult(2, poems, 0, 2);
    }
}
