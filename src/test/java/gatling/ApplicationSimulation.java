package gatling;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class ApplicationSimulation extends Simulation {
  HttpProtocolBuilder httpProtocol =
      http.baseUrl("http://localhost:8080");

  ScenarioBuilder myScenario = scenario("My Scenario")
      .exec(http("Request 1").post("/v1/tasks").body(StringBody("""
          {"name":"test",
          "urls":[]}
          """)).asJson().check(status().is(202)),
          http("Request 2").get("/v1/tasks/test").check(status().is(200))
          );

  {
    setUp(
        myScenario.injectOpen(constantUsersPerSec(50).during(60))
    ).protocols(httpProtocol)
        .assertions(global().responseTime().percentile3().lte(200))
    ;
  }
}
