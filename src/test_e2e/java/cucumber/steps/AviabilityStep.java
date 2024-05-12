package cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cucumber.runner.ConfigurationSingleton;
import io.cucumber.java.en.Then;
import java.net.URL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AviabilityStep {
  @Then("server is running")
  public void somethingHappens() {
    URL baseURl = ConfigurationSingleton.get().baseUrl();
    RestTemplate client = new RestTemplate();
    ResponseEntity<String> response =
        client.getForEntity(baseURl + "/actuator/health/readiness", String.class);
    assertTrue(response.getStatusCode().is2xxSuccessful());
  }
}
