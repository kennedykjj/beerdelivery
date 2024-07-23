package beer.delivery.challenge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "beer.delivery.challenge.repository")
@Configuration
public class Config {

}
