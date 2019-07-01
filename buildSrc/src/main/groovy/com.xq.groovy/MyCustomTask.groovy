import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class MyCustomTask extends DefaultTask {
    @TaskAction
    void output() {
        println("==================================")
        //引用extension的值
        println "Hello this is my custom task output ： ${project.testExt1.message}"
        println "Hello this is my custom task output ： ${project.testExt2.message}"
        println "Hello this is my custom task output ： ${project.testExt1.nestArgs.msg}"
        println("==================================")
    }
}