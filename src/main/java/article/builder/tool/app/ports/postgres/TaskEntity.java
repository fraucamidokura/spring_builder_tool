package article.builder.tool.app.ports.postgres;

import article.builder.tool.app.task.Status;
import article.builder.tool.app.task.Task;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.net.URL;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table
public class TaskEntity {

  @Id
  private String name;

  @ElementCollection
  private List<URL> urls;

  private Status status;

  public static class Adapter{

    private Adapter() {
    }

    public static Task toBusiness(TaskEntity entity){
      return Task.builder()
          .name(entity.getName())
          .urls(entity.getUrls())
          .status(entity.getStatus()).build();
    }

    public static TaskEntity toEntity(Task task){
      TaskEntity entity = new TaskEntity();
      entity.setName(task.name());
      entity.setUrls(task.urls());
      entity.setStatus(task.status());
      return  entity;
    }
  }
}

