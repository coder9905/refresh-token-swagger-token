package uz.zako;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class RefreshTokenSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefreshTokenSpringBootApplication.class, args);
    }

}
