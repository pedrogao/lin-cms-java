package com.lin.cms.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源文件路径，可以是数据多个文件地址
 * 可以是classpath地址如：
 * "classpath:/com/myco/app.properties"
 * 也可以是对应的文件系统地址如：
 * "file:/path/to/file"
 */

@SpringBootApplication(scanBasePackages = {"com.lin.cms"})
@MapperScan(basePackages = {"com.lin.cms.demo.mapper"/*, "com.lin.cms.plugins.poem.app"*/})
@PropertySources({
        // @PropertySource("classpath:com/lin/cms/plugins/poem/plugin.properties"),
        @PropertySource(value = "classpath:errorcode.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:com/lin/cms/demo/extensions/file/config.properties", encoding = "UTF-8")
})
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "<style type=\"text/css\">*{ padding: 0; margin: 0; } div{ padding: 4px 48px;} a{color:#2E5CD5;cursor:" +
                "pointer;text-decoration: none} a:hover{text-decoration:underline; } body{ background: #fff; font-family:" +
                "\"Century Gothic\",\"Microsoft yahei\"; color: #333;font-size:18px;} h1{ font-size: 100px; font-weight: normal;" +
                "margin-bottom: 12px; } p{ line-height: 1.6em; font-size: 42px }</style><div style=\"padding: 24px 48px;\"><p>" +
                "Lin <br/><span style=\"font-size:30px\">心上无垢，林间有风。</span></p></div> ";
    }

}
