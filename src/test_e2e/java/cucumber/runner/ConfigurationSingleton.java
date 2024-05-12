package cucumber.runner;

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
    return singleton.get();
  }
}
