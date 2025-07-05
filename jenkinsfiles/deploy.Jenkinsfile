// jenkinsfiles/deploy.Jenkinsfile
// Jenkinsfile 示例：通过 shared library 的 deploy.groovy 实现远程部署
// 需在 Jenkins 系统配置中注册 shared library，并预装 sshpass/ssh

deploy {
    git = 'https://github.com/your/repo.git'      // 代码仓库地址
    host = '1.2.3.4'                              // 远程主机
    user = 'deployuser'                           // SSH 用户名
    password = 'yourpassword'                     // SSH 密码（或用 privateKey）
    // privateKey = credentials('your-ssh-key-id') // SSH 私钥内容（可选）
    projectDir = 'repo'                           // 仓库目录名（git pull 用）
}

// 说明：
// - deploy.groovy 会自动调用 Deploy 工具类，完成 clone、pull、deploy
// - 支持密码或密钥认证，按需填写
// - 详细日志和错误会输出到 Jenkins 控制台