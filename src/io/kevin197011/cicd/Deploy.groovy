package io.kevin197011.cicd

/**
 * Deploy class: 支持通过 SshUtils 在 pipeline 远程部署
 */
class Deploy extends AbstractDeploy {

    private String repo
    private String host
    private String user
    private String password  // 可选
    private String privateKey // 可选
    private String workDir = '/tmp/deploy' // 默认远程工作目录

    Deploy(String repo, String host, String user, String password = null, String privateKey = null) {
        this.repo = repo
        this.host = host
        this.user = user
        this.password = password
        this.privateKey = privateKey
    }

    @Override
    boolean gitClone(String repo, steps) {
        String cmd = "sudo mkdir -p ${workDir} && cd ${workDir} && sudo git clone ${repo}"
        return execRemote(cmd, steps)
    }

    @Override
    boolean gitPull(String path, steps) {
        String cmd = "cd ${workDir}/${path} && sudo git pull"
        return execRemote(cmd, steps)
    }

    @Override
    boolean doDeploy(steps) {
        String cmd = "cd ${workDir} && sudo ./deploy.sh"
        return execRemote(cmd, steps)
    }

    @Override
    void validate() {
        assert repo
        assert host
        assert user
        assert (password || privateKey)
    }

    private boolean execRemote(String cmd, steps) {
        String result
        if (privateKey) {
            result = SshUtils.execWithKey(host, user, privateKey, cmd, steps)
        } else if (password) {
            result = SshUtils.execWithPassword(host, user, password, cmd, steps)
        } else {
            throw new IllegalArgumentException('No SSH credentials provided')
        }
        LoggerUtils.info("[Deploy] ${cmd} => ${result}", steps)
        return result != null && result != ''
    }

    @Override
    public String toString() {
        return 'Deploy{' +
                "repo='" + repo + '\'' +
                ", host='" + host + '\'' +
                ", user='" + user + '\'' +
                '}'
    }
}
