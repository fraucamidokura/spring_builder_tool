package cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Then;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AviabilityStep {

  @Then("server is running")
  public void somethingHappens(){
    RestTemplate client = new RestTemplate();
    ResponseEntity<String> response = client.getForEntity("http://localhost:8080/actuator/health/readiness",String.class);
    assertTrue(response.getStatusCode().is2xxSuccessful());
  }
}
