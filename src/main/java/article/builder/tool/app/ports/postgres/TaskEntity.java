package article.builder.tool.app.ports.postgres;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.net.URL;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(indexes = @Index(name="uq_task_name",columnList = "name",unique = true))
public class TaskEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ElementCollection
  private List<URL> urls;
}
