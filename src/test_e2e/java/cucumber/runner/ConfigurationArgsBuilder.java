package cucumber.runner;

import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ConfigurationArgsBuilder {

  static Option urlOption =
      Option.builder("u")
          .longOpt("url")
          .hasArg()
          .required()
          .desc("Url where the end to end test will be run against")
          .type(URL.class)
          .build();

  static Options buildOptions() {
    Options options = new Options();
    options.addOption(urlOption);
    options.addOption("h", "help", false, "Print help");
    options.addOption("g", "glue", true, "Cucumber argument");
    return options;
  }

  public static Configuration parse(String[] args) throws ParseException {
    CommandLineParser parser = new DefaultParser(true);
    Options options = buildOptions();
    CommandLine cmd = parser.parse(options, args);
    if (cmd.hasOption("help")) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("run-test", options);
    }
    return parse(cmd);
  }

  public static String[] cucumberArgs(String[] args) throws ParseException {
    CommandLineParser parser = new DefaultParser(true);
    Options options = buildOptions();
    CommandLine cmd = parser.parse(options, args);
    ArrayList<String> cucumeberArgs = new ArrayList<>();
    for (Option opt : cmd.getOptions()) {
      if (opt.getDescription().indexOf("Cucumber") >= 0) {
        cucumeberArgs.add("--" + opt.getLongOpt());
        cucumeberArgs.addAll(opt.getValuesList());
      }
    }
    cucumeberArgs.addAll(cmd.getArgList());
    return cucumeberArgs.toArray(new String[0]);
  }

  static Configuration parse(CommandLine cmd) throws ParseException {
    return new Configuration(cmd.getParsedOptionValue(urlOption));
  }
}
