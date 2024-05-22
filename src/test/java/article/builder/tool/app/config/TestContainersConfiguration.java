package article.builder.tool.app.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;


@TestConfiguration
public class TestContainersConfiguration {
  @Bean
  @ServiceConnection
  public PostgreSQLContainer<?> postgresDB(){
    return new PostgreSQLContainer<>("postgres:16.3");
  }

}
