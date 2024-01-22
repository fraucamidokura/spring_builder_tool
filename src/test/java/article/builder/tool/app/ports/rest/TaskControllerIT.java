package article.builder.tool.app.ports.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import article.builder.tool.app.AbstractIntegrationTest;
import article.builder.tool.app.task.TaskSampler;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class TaskControllerIT extends AbstractIntegrationTest {

  @Test
  void sendTask() throws Exception{
    client.perform(
        post("/v1/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(TaskSampler.sample()))
    ).andExpect(
        status().isAccepted()
    );
  }
}
