package cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import article.builder.tool.app.task.Task;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TasksSteps {
  RestTemplate client = ClientSingleton.get();
  List<Task> addedTasks = new ArrayList<>();

  @When("I add a task")
  public void iAddATask() {
    final var name = "task"+ RandomStringUtils.random(5);
    final Task original = Task.builder().name(name)
        .build();
    final ResponseEntity<Task> response = client.postForEntity("/v1/tasks", original, Task.class);
    assertTrue(response.getStatusCode().is2xxSuccessful());
    final Task created = response.getBody();
    addedTasks.add(created);
    assertThat(created).isNotNull();
    assertThat(created.name()).isEqualTo(original.name());
  }

  @Then("I can find the task")
  public void iCanFindTheTask() {
    final Task lastAdded = addedTasks.getLast();
    final String url = "/v1/tasks/%s".formatted(lastAdded.name());
    final ResponseEntity<Task> response = client.getForEntity(url, Task.class);
    assertTrue(response.getStatusCode().is2xxSuccessful());
    final Task found = response.getBody();
    assertThat(found).isNotNull();
    assertThat(found.name()).isEqualTo(lastAdded.name());
  }

  @And("I delete the task")
  public void iDeleteTheTask() {
    final Task lastAdded = addedTasks.getLast();
    delete(lastAdded.name());
  }

  @Then("I can not found the task")
  public void iCanNotFoundTheTask() {
    final Task lastAdded = addedTasks.getLast();
    final String url = "/v1/tasks/%s".formatted(lastAdded.name());
    try {
      client.getForEntity(url, Task.class);
    }catch (HttpClientErrorException e){
      assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
  }

  @Before
  public void initialize(){
    this.addedTasks.clear();
  }

  @After
  public void clean(){
    for(Task task:addedTasks){
      delete(task.name());
    }
    addedTasks.clear();
  }

  private void delete(String name){
    final String url = "/v1/tasks/%s".formatted(name);
    client.delete(url);
  }
}
