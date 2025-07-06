package io.kevin197011.cicd

import groovy.json.JsonSlurper
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory
import java.net.URL

/**
 * HarborUtils: Harbor 镜像仓库相关工具（原生 Groovy 实现）
 */
class HarborUtils {
    /**
     * 获取 Harbor 镜像 tag 列表（原生实现）
     * @param harborHost harbor.example.com
     * @param project    项目名
     * @param repo       仓库名
     * @param username   用户名（可选）
     * @param password   密码（可选）
     * @return List<String> tag 列表
     */
    static List<String> getTags(String harborHost, String project, String repo, String username = null, String password = null) {
        def apiUrl = "https://${harborHost}/api/v2.0/projects/${project}/repositories/${repo}/artifacts"
        def url = new URL(apiUrl)
        def connection = (HttpsURLConnection) url.openConnection()
        connection.setRequestMethod("GET")
        connection.setConnectTimeout(5000)
        connection.setReadTimeout(5000)
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("User-Agent", "Groovy-HarborUtils")
        if (username && password) {
            def auth = "${username}:${password}".bytes.encodeBase64().toString()
            connection.setRequestProperty("Authorization", "Basic ${auth}")
        }
        connection.setSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.default)
        int responseCode = connection.responseCode
        if (responseCode != 200) {
            throw new RuntimeException("Harbor API 请求失败: HTTP ${responseCode}")
        }
        def response = connection.inputStream.getText("UTF-8")
        def artifacts = new JsonSlurper().parseText(response)
        def tags = artifacts.collect { it.tags[0]?.name }.findAll { it }
        if (!tags) {
            throw new RuntimeException("未获取到任何镜像 tag")
        }
        return tags
    }
}