package article.builder.tool.app;

import article.builder.tool.app.config.TestContainersConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
        .profiles("test")
        .sources(TestContainersConfiguration.class)
        .run(args);
  }
}
