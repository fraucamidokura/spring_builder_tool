import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class DockerPushTask extends DefaultTask{
    @Input
    abstract Property<String> getImageName()

    DockerPushTask(){
        imageName.convention("ghcr.io/fraucamidokura/task-sample:${project.version}")
    }
    @TaskAction
    void push(){
        println "Will push to ghcr "+imageName.get()
        project.exec {
            commandLine "docker", "push", "${imageName.get()}"
        }
    }
}
