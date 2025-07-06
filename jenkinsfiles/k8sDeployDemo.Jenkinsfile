// jenkinsfiles/k8sDeployDemo.Jenkinsfile
// Jenkinsfile 示例：调用 shared library 的 k8sDeployDemo，支持 Harbor 镜像 tag 交互选择
// 需在 Jenkins 系统配置中注册 shared library，并配置 Harbor 用户名密码凭据

k8sDeployDemo(
    repo: 'harbor.example.com/project/app',         // Harbor 镜像仓库地址
    credId: 'harbor-credentials-id'                 // Jenkins中配置的Harbor用户名密码凭据ID
)

// 说明：
// - 运行时会自动获取 Harbor 镜像 tag 列表，弹窗让用户选择
// - 选择的 tag 会用于后续 K8s 部署
// - credId 建议用"用户名和密码"类型凭据