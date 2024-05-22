package article.builder.tool.app.ports.postgres;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import article.builder.tool.app.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class TaskRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  TaskRepository repo;

  @Test
  void testSimpleUse(){
    var name = "commonName";
    var task = new TaskEntity();
    task.setName(name);

    task = repo.save(task);

    assertThat(task.getId()).isNotNull();

    var foundTask = repo.findById(task.getId());

    assertThat(foundTask).isNotEmpty();
    assertThat(foundTask.get().getName()).isEqualTo(task.getName());

    repo.deleteById(task.getId());

    foundTask = repo.findById(task.getId());

    assertThat(foundTask).isEmpty();
  }

  @Test
  void testRepeatName(){
    var name = "commonName";
    var task = new TaskEntity();
    task.setName(name);

    repo.save(task);

    var duplicated = new TaskEntity();
    duplicated.setName(name);

    assertThatThrownBy(()->repo.save(duplicated)).isInstanceOf(DataIntegrityViolationException.class)
        .hasMessageContaining("uq_task_name");
  }
}
