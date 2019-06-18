package com.lin.cms.demo.plugins.poem.app;

import com.lin.cms.core.result.Result;
import com.lin.cms.core.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plugin/poem/")
public class PoemController {

    @Value("${lin.cms.poem.limit}")
    private Integer limit;

    @GetMapping
    public Result index() {
        return ResultGenerator.genSuccessResult(limit);
    }
}
