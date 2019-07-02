package com.xq.groovy

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyCustomPlugin implements Plugin<Project> {
    void apply(Project project) {
        //1.添加插件扩展到project.extensions集合中
        project.extensions.create("testExt1", TestExtension)
        project.extensions.add("testExt2", TestExtension)
        //嵌套
        project.testExt1.extensions.create('nestArgs', NestExtension)

        //运行命令：gradle :app:myTask
        project.task('myTask') << {
            println "========================"
            println "hello task of gradle plugin!"
            println "========================"
            println "============================================"
            println "Execution执行阶段"
            println "执行具体的task及其依赖的task"
            println "============================================"

            //2.获取外界配置的 TestExtension
            TestExtension extension1 = project.testExt1
            TestExtension extension2 = project.testExt2
            NestExtension extension3 = project.testExt1.nestArgs
            //3.输出插件扩展属性
            println "========================"
            println extension1.message
            println extension2.message
            println extension3.msg
            println "========================"
        }
        //groovy支持Java
        System.out.println("========================")
        System.out.println("你好，gradle 插件!")
        System.out.println("========================")

        //运行命令：gradle :app:customTask
        project.task('customTask', type: MyCustomTask)

    }
}
