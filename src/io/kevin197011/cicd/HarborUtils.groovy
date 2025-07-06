package io.kevin197011.cicd

/**
 * HarborUtils: Harbor 镜像仓库相关工具
 */
class HarborUtils {
    /**
     * 获取 Harbor 镜像 tag 列表
     * @param harborHost harbor.example.com
     * @param project    项目名
     * @param repo       仓库名
     * @param steps      pipeline steps（如 this）
     * @param credId     Jenkins 凭据ID（可选）
     * @return List<String> tag 列表
     */
    static List<String> getTags(String harborHost, String project, String repo, def steps, String credId = null) {
        def tagsJson = ''
        if (credId) {
            steps.withCredentials([steps.usernamePassword(credentialsId: credId, usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASS')]) {
                tagsJson = steps.sh(
                    script: "curl -s -k -u \"$HARBOR_USER:$HARBOR_PASS\" \"https://${harborHost}/api/v2.0/projects/${project}/repositories/${repo}/artifacts\"",
                    returnStdout: true
                )
            }
        } else {
            tagsJson = steps.sh(
                script: "curl -s -k \"https://${harborHost}/api/v2.0/projects/${project}/repositories/${repo}/artifacts\"",
                returnStdout: true
            )
        }
        def tags = []
        try {
            def artifacts = steps.readJSON text: tagsJson
            tags = artifacts.collect { it.tags[0]?.name }.findAll { it }
        } catch (e) {
            steps.error "获取 Harbor 镜像 tag 失败: ${e}"
        }
        if (!tags) {
            steps.error "未获取到任何镜像 tag"
        }
        return tags
    }
}