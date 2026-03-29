import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.*
import org.jacoco.core.instr.Instrumenter
import org.jacoco.core.runtime.OfflineInstrumentationAccessGenerator
import java.io.File

abstract class JacocoOfflineInstrumentTask : DefaultTask() {

    @get:InputDirectory
    abstract val inputDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun instrument() {
        val inDir = inputDirectory.get().asFile
        val outDir = outputDirectory.get().asFile
        outDir.deleteRecursively()
        outDir.mkdirs()

        val instrumenter = Instrumenter(OfflineInstrumentationAccessGenerator())
        var count = 0

        inDir.walkTopDown()
            .filter { it.isFile && it.extension == "class" }
            .filter { f ->
                val name = f.name
                !name.startsWith("ComposableSingletons") &&
                !name.startsWith("BuildConfig") &&
                name != "R.class" &&
                !name.startsWith("R\$")
            }
            .forEach { classFile ->
                val rel = classFile.relativeTo(inDir)
                val outFile = File(outDir, rel.path)
                outFile.parentFile.mkdirs()
                val instrumented = classFile.inputStream().use { ins ->
                    instrumenter.instrument(ins, classFile.name)
                }
                outFile.writeBytes(instrumented)
                count++
            }

        logger.lifecycle("JaCoCo offline: instrumented $count classes → ${outDir.absolutePath}")
    }
}
