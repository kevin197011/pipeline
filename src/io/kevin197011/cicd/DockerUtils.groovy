package io.kevin197011.cicd

/**
 * DockerUtils: Docker 镜像构建与推送工具类
 */
class DockerUtils {
    /**
     * 构建 Docker 镜像
     * @param steps Jenkins pipeline steps
     * @param imageName 镜像名:tag
     * @param contextDir Dockerfile 所在目录，默认当前目录
     */
    static void build(def steps, String imageName, String contextDir = '.') {
        steps.dir(contextDir) {
            LoggerUtils.info("构建 Docker 镜像: ${imageName}", steps)
            steps.sh "docker build -t ${imageName} ."
        }
    }

    /**
     * 推送 Docker 镜像到远程仓库（支持 Harbor 凭证登录）
     * @param steps Jenkins pipeline steps
     * @param imageName 镜像名:tag
     * @param registry Harbor 地址（如 harbor.example.com）
     * @param credentialsId Jenkins 凭证ID（用户名/密码类型）
     */
    static void push(def steps, String imageName, String registry, String credentialsId) {
        steps.withCredentials([
            steps.usernamePassword(credentialsId: credentialsId, usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASSWORD')
        ]) {
            LoggerUtils.info("登录 Harbor: ${registry}", steps)
            steps.sh "echo \"$HARBOR_PASSWORD\" | docker login ${registry} -u \"$HARBOR_USER\" --password-stdin"
            LoggerUtils.info("推送 Docker 镜像: ${imageName}", steps)
            steps.sh "docker push ${imageName}"
        }
    }

    /**
     * 推送 Docker 镜像到远程仓库
     * @param steps Jenkins pipeline steps
     * @param imageName 镜像名:tag
     */
    static void push(def steps, String imageName) {
        LoggerUtils.info("推送 Docker 镜像: ${imageName}", steps)
        steps.sh "docker push ${imageName}"
    }

    /**
     * 登录 Harbor 或任意 Docker Registry
     * @param steps Jenkins pipeline steps
     * @param registry 仓库地址（如 harbor.example.com）
     * @param username 用户名
     * @param password 密码
     */
    static void login(def steps, String registry, String username, String password) {
        LoggerUtils.info("登录 Harbor: ${registry}", steps)
        steps.sh "echo '${password}' | docker login ${registry} -u '${username}' --password-stdin"
    }

    /**
     * 镜像打 tag
     * @param steps Jenkins pipeline steps
     * @param sourceImage 源镜像名:tag
     * @param targetImage 目标镜像名:tag
     */
    static void tag(def steps, String sourceImage, String targetImage) {
        LoggerUtils.info("镜像打 tag: ${sourceImage} -> ${targetImage}", steps)
        steps.sh "docker tag ${sourceImage} ${targetImage}"
    }
}