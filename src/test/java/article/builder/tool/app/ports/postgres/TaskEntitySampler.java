package article.builder.tool.app.ports.postgres;

import article.builder.tool.app.task.Task;
import article.builder.tool.app.task.TaskSampler;
import java.net.MalformedURLException;

public class TaskEntitySampler {
  public static TaskEntity sample() throws MalformedURLException {
    Task task = TaskSampler.sample();
    return TaskEntity.Adapter.toEntity(task);
  }
}
