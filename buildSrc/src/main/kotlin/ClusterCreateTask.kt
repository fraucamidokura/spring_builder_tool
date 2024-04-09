import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ClusterCreateTask : DefaultTask() {

    @TaskAction
    fun execute() {
        if(!clusterExists()){
            "kind create cluster --name builder".runCommand()
        }
    }
}