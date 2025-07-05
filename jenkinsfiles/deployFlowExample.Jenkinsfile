// jenkinsfiles/deployFlowExample.Jenkinsfile
// Jenkinsfile 示例：调用 shared library 的 deployFlowExample pipeline
// 需在 Jenkins 系统配置中注册 shared library（如 your-shared-lib）

@Library('devops-lib') _

deployFlowExample(
    env: 'production',
    image: 'myapp:1.0.0',
    deployUser: 'jenkins',
    extraParam: 'demo'
)

// 说明：
// - deployFlowExample 是 vars/deployFlowExample.groovy 封装的 pipeline 入口
// - 参数可根据实际需要扩展，最终会传递给 DeployFlow.groovy
// - 日志和流水线结构由 DeployFlow 决定