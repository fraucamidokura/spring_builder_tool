import org.gradle.api.GradleException
import java.util.concurrent.TimeUnit

fun String.runCommand(): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    if ( proc.exitValue() > 0 ){
        println("Throw exception")
        throw GradleException(proc.errorStream.bufferedReader().readText())
    }
    return proc.inputStream.bufferedReader().readText()
}