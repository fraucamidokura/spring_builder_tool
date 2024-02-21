import java.io.ByteArrayOutputStream
import org.gradle.api.Project

fun Project.getGitSHA(): String {
  val stdout = ByteArrayOutputStream()
  val command = "git rev-parse HEAD"
  exec {
    commandLine("bash", "-c", command)
    standardOutput = stdout
  }
  return stdout.toString().trim()
}
