package article.builder.tool.app.ports.postgres;

import static org.assertj.core.api.Assertions.assertThat;

import article.builder.tool.app.AbstractIntegrationTest;
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

    task = repo.save(task);


    var foundTask = repo.findById(task.getName());

    assertThat(foundTask).isNotEmpty();
    assertThat(foundTask.get().getName()).isEqualTo(task.getName());

    repo.deleteById(task.getName());

    foundTask = repo.findById(task.getName());

    assertThat(foundTask).isEmpty();
  }
}
