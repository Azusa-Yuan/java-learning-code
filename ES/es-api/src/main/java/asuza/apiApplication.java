package asuza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@SpringBootApplication
@Configuration
public class apiApplication {
    public static void main(String[] args) {
        SpringApplication.run(apiApplication.class, args);
    }
}
