import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SuppressWarnings("SpringBootApplicationSetup")
@SpringBootApplication(scanBasePackages = {"com.lin.cms.struct", "auth"})
@MapperScan(basePackages = {"com.lin.cms.struct.mapper"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
