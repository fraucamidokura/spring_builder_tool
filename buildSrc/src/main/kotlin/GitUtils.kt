import org.gradle.api.Project

fun Project.getGitSHA(): String {
  return "git rev-parse HEAD".runCommand().trim()
}
