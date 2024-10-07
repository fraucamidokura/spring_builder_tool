package article.builder.tool.app.ports.postgres;

import static org.assertj.core.api.Assertions.assertThat;

import article.builder.tool.app.AbstractIntegrationTest;
import article.builder.tool.app.task.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TaskRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  TaskRepository repo;

  @Test
  void testSimpleUse(){
    var name = "commonName";
    var task = new TaskEntity();
    task.setName(name);
    task.setStatus(Status.CREATED);

    task = repo.save(task);


    var foundTask = repo.findById(task.getName());

    assertThat(foundTask).isNotEmpty();
    assertThat(foundTask.get().getName()).isEqualTo(task.getName());
    assertThat(foundTask.get().getStatus()).isEqualTo(Status.CREATED);

    repo.deleteById(task.getName());

    foundTask = repo.findById(task.getName());

    assertThat(foundTask).isEmpty();
  }
}
