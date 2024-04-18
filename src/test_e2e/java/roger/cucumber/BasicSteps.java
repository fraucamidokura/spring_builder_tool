package roger.cucumber;

import io.cucumber.java.en.When;

public class BasicSteps {

  @When("something happens")
  public void somethingHappens(){
    System.out.println("hola");
  }
}
