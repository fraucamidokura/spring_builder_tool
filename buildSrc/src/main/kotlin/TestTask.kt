import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TestTask:DefaultTask() {
    @TaskAction
    fun test(){

    }
}