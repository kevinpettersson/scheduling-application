package timebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Main class for the application.
 *
 * @since 2024-12-19
 * @author Group 12
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "timebridge.repository")
@ComponentScan(basePackages = { "timebridge", "timebridge.config", "timebridge.services" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
