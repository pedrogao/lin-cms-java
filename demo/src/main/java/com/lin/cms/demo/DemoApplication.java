package com.lin.cms.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 资源文件路径，可以是数据多个文件地址
 * 可以是classpath地址如：
 * "classpath:/com/myco/app.properties"
 * 也可以是对应的文件系统地址如：
 * "file:/path/to/file"
 */

@SpringBootApplication(scanBasePackages = {"com.lin.cms"})
@MapperScan(basePackages = {"com.lin.cms.demo.mapper", "com.lin.cms.plugins.poem.app"})
@PropertySources({
        @PropertySource("classpath:com/lin/cms/plugins/poem/plugin.properties"),
        @PropertySource("classpath:com/lin/cms/demo/extensions/file/config.properties")
})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
