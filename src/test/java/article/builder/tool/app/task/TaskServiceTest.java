package article.builder.tool.app.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.doReturn;

import article.builder.tool.app.ports.postgres.TaskEntity;
import article.builder.tool.app.ports.postgres.TaskEntitySampler;
import article.builder.tool.app.ports.postgres.TaskRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock
  TaskRepository repo;
  @InjectMocks
  TaskService service;

  @Test
  void findByName() throws Exception{
    TaskEntity entity = TaskEntitySampler.sample();
    doReturn(Optional.of(entity)).when(repo).findByName(entity.getName());

    Task found = service.findByName(entity.getName());

    assertThat(found.name()).isEqualTo(entity.getName());
    assertThat(found.urls()).isEqualTo(entity.getUrls());
  }
  @Test
  void nofFoundByName() throws  Exception{
    TaskEntity entity = TaskEntitySampler.sample();
    String name = entity.getName();
    doReturn(Optional.empty()).when(repo).findByName(entity.getName());

    ResponseStatusException error = catchThrowableOfType(()->service.findByName(name),ResponseStatusException.class);

    assertThat(error.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    assertThat(error.getMessage()).contains("Could not find '%s'".formatted(entity.getName()));
  }

}
