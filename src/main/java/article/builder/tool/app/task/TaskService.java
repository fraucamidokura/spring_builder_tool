package article.builder.tool.app.task;

import static article.builder.tool.app.ports.postgres.TaskEntity.Adapter.toBusiness;
import static article.builder.tool.app.ports.postgres.TaskEntity.Adapter.toEntity;

import article.builder.tool.app.ports.postgres.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class TaskService {

  private final TaskRepository repo;

  public TaskService(TaskRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public Task save(Task task) {
    return toBusiness(
        repo.save(toEntity(task))
    );
  }

  @Transactional
  public Task findByName(String name) {
    return toBusiness(
        repo.findByName(name)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "Could not find '%s'".formatted(name)))
    );
  }

  @Transactional
  public void deleteByName(String name) {
    Long removed = this.repo.deleteByName(name);
    if (removed>1 || removed<0){
      log.error("We have deleted %s by name '%s'".formatted(removed,name));
    }
  }
}
