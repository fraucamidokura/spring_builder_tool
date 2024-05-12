package article.builder.tool.app.ports.rest;

import article.builder.tool.app.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

  @PostMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Task addTask(@RequestBody Task task) {
    return task;
  }
}
