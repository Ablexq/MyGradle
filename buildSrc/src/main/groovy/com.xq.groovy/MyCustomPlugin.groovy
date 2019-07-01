package com.xq.groovy

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyCustomPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('myTask') << {
            println "========================"
            println "hello task of gradle plugin!"
            println "========================"
        }
        //groovy支持Java
        System.out.println("========================")
        System.out.println("你好，gradle 插件!")
        System.out.println("========================")
    }
}
