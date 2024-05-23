package cucumber.runner;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationSingleton {
  static AtomicReference<Configuration> singleton = new AtomicReference<>();

  public static void initialize(Configuration config) {
    Configuration previous = singleton.get();
    if (previous != null) {
      throw new ConfigurationException("Configuration could not be set twice");
    }
    if (!singleton.compareAndSet(previous, config)) {
      throw new ConfigurationException("Race condition try to initialize configuration");
    }
  }

  public static Configuration get() {
    return singleton.accumulateAndGet(defaultConfiguration(),(current,def)->{
      if(current==null){
        return def;
      }
      return current;
    });
  }

  private static Configuration defaultConfiguration(){
    try {
      return new Configuration(new URI("http://localhost:8080").toURL());
    } catch (MalformedURLException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
