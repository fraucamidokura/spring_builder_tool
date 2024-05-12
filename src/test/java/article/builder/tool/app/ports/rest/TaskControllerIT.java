package article.builder.tool.app.ports.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import article.builder.tool.app.AbstractIntegrationTest;
import article.builder.tool.app.task.Task;
import article.builder.tool.app.task.TaskSampler;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class TaskControllerIT extends AbstractIntegrationTest {

  @Test
  void sendTask() throws Exception {
    Task task = TaskSampler.sample();
    List<String> string_urls = task.urls().stream().map(URI::toString).toList();
    client
        .perform(
            post("/v1/tasks").contentType(MediaType.APPLICATION_JSON).content(asJsonString(task)))
        .andExpect(status().isAccepted())
        .andExpect(jsonPath("$.name", is(task.name())))
        .andExpect(jsonPath("$.urls", is(string_urls)));
  }
}
