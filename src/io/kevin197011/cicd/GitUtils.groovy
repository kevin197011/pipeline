package io.kevin197011.cicd

/**
 * GitUtils: Git 操作工具类
 */
class GitUtils {
    /**
     * 拉取 GitLab 仓库
     * @param steps Jenkins pipeline steps
     * @param repoUrl 仓库地址
     * @param credentialsId Jenkins 凭证 ID
     * @param branch 分支名，默认 master
     * @param targetDir 拉取到的目录，默认 '.'
     */
    static void pull(def steps, String repoUrl, String credentialsId, String branch = 'master', String targetDir = '.') {
        LoggerUtils.info("拉取 GitLab 仓库: ${repoUrl}，分支: ${branch}，目录: ${targetDir}", steps)
        steps.dir(targetDir) {
            steps.checkout([
                $class: 'GitSCM',
                branches: [[name: "*/${branch}"]],
                userRemoteConfigs: [[
                    url: repoUrl,
                    credentialsId: credentialsId
                ]]
            ])
        }
    }
}