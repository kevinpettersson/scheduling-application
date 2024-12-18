package timebridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Main class for the application.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = "timebridge.repository")
@ComponentScan(basePackages = { "timebridge", "timebridge.config", "timebridge.services" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
