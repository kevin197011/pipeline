// vars/deployFlowExample.groovy
// Jenkins Shared Library 示例：如何在 vars 下调用 DeployFlow.groovy
// 用法：在 Jenkinsfile 里直接调用 deployFlowExample()

def call(Map config = [:]) {
    // 导入 DeployFlow
    def flow = new io.kevin197011.cicd.DeployFlow()
    // 调用 apply(config) 返回 pipeline 闭包并执行
    flow.apply(config).call()
}

// ---
// Jenkinsfile 示例：
// @Library('devops-lib') _
// deployFlowExample(env: 'prod', image: 'myapp:latest')