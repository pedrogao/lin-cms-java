import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * application.properties
 */

@SuppressWarnings("SpringBootApplicationSetup")
@SpringBootApplication(scanBasePackages = {"com.lin.cms.plugins.poem", "auth"})
@MapperScan(basePackages = {"com.lin.cms.plugins.poem.app"})
@PropertySources({
        @PropertySource("classpath:com/lin/cms/plugins/poem/plugin.properties")
})
public class MockApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockApplication.class, args);
    }
}
