import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class ClusterCreateTask : DefaultTask() {
  @get:Input abstract val imageName: Property<String>
  @get:Input abstract val imageTag: Property<String>

  @TaskAction
  fun execute() {
    if (!clusterExists()) {
      println("Creating cluster")
      "kind create cluster --name builder --config buildSrc/src/main/resources/kind-cluster.yaml"
          .runCommand()
    }
    println("Load image")
    "kind --name builder load docker-image ${imageName.get()}".runCommand()
    println("update charts")
    "helm dependency build ./charts".runCommand()
    println("Deploy helm image")
    ("helm upgrade --install local ./charts --create-namespace --namespace builder --wait --atomic " +
            "--set image.tag=${imageTag.get()} " +
            "--set service.type=NodePort --set service.nodePort=31080")
        .runCommand()
  }
}
