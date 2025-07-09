// jenkinsfiles/imagePush.Jenkinsfile
// Jenkinsfile 示例：通过 shared library 的 imagePush.groovy 实现一键拉取、构建、推送 Harbor 镜像
// 需在 Jenkins 系统配置中注册 shared library，并预装 Docker

@Library('devops-lib') _
imagePush {
    gitlabRepoUrl = 'https://gitlab.example.com/group/project.git'   // GitLab 仓库地址
    branch = 'main'                                                 // 分支名
    credentialsId = 'gitlab-cred'                                   // Jenkins Git 凭证ID
    imageName = 'harbor.example.com/project/image:tag'              // Docker 镜像名:tag
    harborUrl = 'harbor.example.com'                                // Harbor 地址
    harborCredentialsId = 'harbor-cred'                             // Jenkins Harbor 凭证ID
}

// 说明：
// - imagePush.groovy 会自动调用 GitUtils、DockerUtils 工具类，完成拉取、构建、推送
// - 所有参数均可通过流水线参数化或直接在此处指定
// - 详细日志和错误会输出到 Jenkins 控制台