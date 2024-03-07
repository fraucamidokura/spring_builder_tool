package article.builder.tool.app.ports.actuator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import article.builder.tool.app.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class ActuatorIT extends AbstractIntegrationTest {

  @Test
  void liveness() throws Exception {
    client.perform(get("/actuator/health/liveness")).andExpect(status().isOk());
  }

  @Test
  void readiness() throws Exception {
    client.perform(get("/actuator/health/readiness")).andExpect(status().isOk());
  }
}
