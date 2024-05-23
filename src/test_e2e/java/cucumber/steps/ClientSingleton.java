package cucumber.steps;

import cucumber.runner.ConfigurationSingleton;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class ClientSingleton {
  static AtomicReference<RestTemplate> singleton = new AtomicReference<>();

  public static synchronized RestTemplate get(){
    final var current = singleton.get();
    if (current == null){
      final var newClient = createClient();
      singleton.set(newClient);
      return newClient;
    }
    return current;
  }

  private static RestTemplate createClient(){
    var baseUrl = ConfigurationSingleton.get().baseUrl();
    return new RestTemplateBuilder()
        .rootUri(baseUrl.toString())
        .build();
  }
}
