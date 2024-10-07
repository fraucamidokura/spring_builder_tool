package article.builder.tool.app.ports.postgres;

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

  public static class Adapter{

    private Adapter() {
    }

    public static Task toBusiness(TaskEntity entity){
      return new Task(entity.getName(),entity.getUrls());
    }

    public static TaskEntity toEntity(Task task){
      TaskEntity entity = new TaskEntity();
      entity.setName(task.name());
      entity.setUrls(task.urls());
      return  entity;
    }
  }
}

