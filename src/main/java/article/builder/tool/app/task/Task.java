package article.builder.tool.app.task;

import java.net.URI;
import java.util.List;
import lombok.Builder;

@Builder
public record Task(String name, List<URI> urls) {

}

