package cucumber.runner;

import io.cucumber.core.cli.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunCucumber {
  static Logger log = LoggerFactory.getLogger(RunCucumber.class);

  public static void main(String[] args) throws Exception {
    log.info("Run cucumber tests");
    ConfigurationSingleton.initialize(ConfigurationArgsBuilder.parse(args));
    byte result = Main.run(ConfigurationArgsBuilder.cucumberArgs(args));
    if (result != 0) {
      throw new RuntimeException("There are errors on the execution");
    }
  }
}
