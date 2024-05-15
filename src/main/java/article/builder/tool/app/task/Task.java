package article.builder.tool.app.task;

import java.net.URL;
import java.util.List;
import lombok.Builder;

@Builder
public record Task(String name, List<URL> urls) {}
