package cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Then;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AviabilitySteps {
  RestTemplate client = ClientSingleton.get();

  @Then("server is running")
  public void somethingHappens() {
    ResponseEntity<String> response =
        client.getForEntity("/actuator/health/readiness", String.class);
    assertTrue(response.getStatusCode().is2xxSuccessful());
  }

}
