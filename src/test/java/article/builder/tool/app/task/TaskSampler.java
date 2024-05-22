package article.builder.tool.app.task;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

public class TaskSampler {

  private TaskSampler() {}

  public static Task sample() throws MalformedURLException {
    return builder().build();
  }

  public static Task.TaskBuilder builder() throws MalformedURLException {
    return Task.builder()
        .name("task" + RandomStringUtils.randomAlphanumeric(12))
        .urls(List.of(randomUrl(), randomUrl(), randomUrl()));
  }

  private static URL randomUrl() throws MalformedURLException {
    return URI.create(
        "https://random.%s.com/sample/%s"
            .formatted(
                RandomStringUtils.randomAlphanumeric(6), RandomStringUtils.randomAlphanumeric(6)))
        .toURL();
  }
}
