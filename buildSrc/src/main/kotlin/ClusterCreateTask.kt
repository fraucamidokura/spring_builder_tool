import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class ClusterCreateTask : DefaultTask() {
    @get:Input abstract val imageName: Property<String>
    @get:Input abstract val imageTag: Property<String>
    @TaskAction
    fun execute() {
        if(!clusterExists()){
            "kind create cluster --name builder".runCommand()
        }
        "kind --name builder load docker-image ${imageName.get()}".runCommand()
        ("helm upgrade --install local ./charts --create-namespace --namespace builder --wait --atomic " +
                "--set image.tag=${imageTag.get()}").runCommand()
    }
}