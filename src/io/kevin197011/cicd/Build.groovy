package io.kevin197011.cicd

/**
 * Build: 支持 Java、Go 编译和 Docker 打包的工具类
 */
class Build {
    /**
     * 编译 Java 项目（自动检测 Maven/Gradle）
     */
    static void buildJava(def steps, String projectDir = '.') {
        steps.dir(projectDir) {
            if (steps.fileExists('pom.xml')) {
                LoggerUtils.info('检测到 Maven 项目，执行 mvn clean package', steps)
                steps.sh 'mvn clean package -DskipTests'
            } else if (steps.fileExists('build.gradle')) {
                LoggerUtils.info('检测到 Gradle 项目，执行 gradle build', steps)
                steps.sh './gradlew build -x test || gradle build -x test'
            } else {
                LoggerUtils.info('未检测到 Maven/Gradle 构建文件', steps)
                throw new RuntimeException('未检测到 Maven/Gradle 构建文件')
            }
        }
    }

    /**
     * 编译 Go 项目
     */
    static void buildGo(def steps, String projectDir = '.') {
        steps.dir(projectDir) {
            LoggerUtils.info('执行 go build', steps)
            steps.sh 'go mod tidy || true'
            steps.sh 'go build ./...'
        }
    }

    /**
     * Docker 镜像打包
     */
    static void buildDocker(def steps, String imageName, String projectDir = '.') {
        steps.dir(projectDir) {
            LoggerUtils.info("执行 docker build -t ${imageName} .", steps)
            steps.sh "docker build -t ${imageName} ."
        }
    }
}
