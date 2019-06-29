import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * application.properties
 */

@SuppressWarnings("ALL")
@SpringBootApplication(scanBasePackages = {"com.lin.cms", "auth"})
@MapperScan(basePackages = {"com.lin.cms.plugins.poem.app","com.lin.cms.base.mapper"})
@PropertySources({
        @PropertySource("classpath:com/lin/cms/plugins/poem/plugin.properties")
})
public class MockApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }
}
