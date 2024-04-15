import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class DockerPushTask : DefaultTask() {
  @get:Input abstract val imageName: Property<String>

  @TaskAction
  fun execute() {
    println("Will push  ${imageName.get()}")
   "docker push ${imageName.get()}".runCommand()
  }
}
