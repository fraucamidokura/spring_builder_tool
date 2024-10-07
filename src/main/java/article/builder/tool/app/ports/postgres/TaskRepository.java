package article.builder.tool.app.ports.postgres;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {
  Optional<TaskEntity> findByName(String name);
  Long deleteByName(String name);
}
