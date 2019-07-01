
# Gradle 的插件可以有三种形式来提供：

> Build script（.gradle中定义）

直接在build.gradle中编写Plugin，这种方式这种方法写的Plugin无法被其他 build.gradle 文件引用。

> buildSrc project（module中定义）

单独的一个Module，这个Module的名称必须为buildSrc，同一个工程中所有的构建文件够可以引用这个插件，但是不能被其他工程引用。

> Standalone project（module中定义）

在一个项目中自定义插件，然后上传到远端maven库等，其他工程通过添加依赖，引用这个插件。
在buildSrc中创建自定义Gradle插件只能在当前项目中使用，因此，对于具有普遍性的插件来说，
通常是建立一个独立的Module来创建自定义Gradle插件。

区别在于：

不需要把 module 的名称的写死，也就是你可以随意的命名，buildSrc会自动的编译和加入到classpath中，这里我们需要手动依赖，需要上传到maven仓库中


1、在当前工程下创建一个 Java Library 的 module，起名字为 buildSrc（别的名字需要maven打包）

2、
将Module里面的内容删除，只保留build.gradle文件和src/main目录。
将 src/main/java 修改为 src/main/groovy ，在groovy包下新建包名，如：com.xq.groovy ，
然后在该包下新建groovy文件，通过new->file-> MyCustomPlugin.groovy来新建名为 MyCustomPlugin 的groovy类文件。
创建的类 MyCustomPlugin 实现 Plugin 接口并覆写 apply(T garget) 接口

Plugin<T> 是一个泛型接口，在定义插件是应该将这个泛型填为 Project 即可。

3、src/main下新建resource文件夹，然后在resources目录里面再新建META-INF目录，
再在META-INF里面新建gradle-plugins目录。最后在gradle-plugins目录里面新建properties文件，
可以自定义，如com.xq.groovy.properties，apply plugin时要用到。
```
# 自定义的gradle plugin的类
implementation-class=com.xq.groovy.MyCustomPlugin
```

4、在外部应用这个插件

//在 app 下的 build.gradle 引用这个插件,
//resources/META-INF/gradle-plugins下的【properties文件名称】
apply plugin: 'com.xq.groovy'

# 打包到本地Maven

如果是自定义名字的module，我们可以本地打包，

```  
apply plugin: 'groovy'
//打包到本地Maven
apply plugin: 'maven'

//打包到本地Maven
repositories {
    mavenCentral()
}

dependencies {
    //gradle sdk
    compile gradleApi()
    //groovy sdk
    compile localGroovy()
}
repositories {
    jcenter()
}

//打包到本地Maven：设置maven deploy
uploadArchives {
    repositories {
        mavenDeployer {
            //设置插件的GAV参数
            pom.groupId = 'com.xq.plugin'
            pom.artifactId = 'test'
            pom.version = '1.0.0'
            //文件发布到下面目录
            repository(url: uri('../repo'))
        }
    }
}
```
然后执行打包命令：

![](imgs/打包.png)

# 引用plugin

``` 
 buildscript {
     repositories {
         maven {//本地Maven仓库地址
             url uri('../repo')
         }
     }
     dependencies {
         //maven本地打包定义的gmv,引用格式为-->group:module:version
         classpath 'com.xq.plugin:test:1.0.0'
     }
 }
 
 //自定义gradle plugin的properties文件名
 apply plugin: 'com.xq.groovy'
```

# 测试

```  

```