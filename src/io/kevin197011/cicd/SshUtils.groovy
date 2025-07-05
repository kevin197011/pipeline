package io.kevin197011.cicd

/**
 * SshUtils: Jenkins Pipeline/Shared Library SSH 执行工具
 * 用法示例：
 *   SshUtils.execWithPassword('1.2.3.4', 'user', 'pass', 'ls -l', steps)
 *   SshUtils.execWithKey('1.2.3.4', 'user', privateKey, 'ls -l', steps)
 */
class SshUtils {
    /**
     * 通过 ssh 密码认证执行远程命令
     * @param host 远程主机
     * @param user 用户名
     * @param password 密码
     * @param command 要执行的命令
     * @param steps pipeline steps（如 this）
     * @return 命令执行结果（stdout）
     */
    static String execWithPassword(String host, String user, String password, String command, steps) {
        def sshCmd = "sshpass -p '${password}' ssh -o StrictHostKeyChecking=no ${user}@${host} '${command.replace("'", "'\\''")}'"
        LoggerUtils.info("[SSH] ${user}@${host} $command", steps)
        return steps.sh(script: sshCmd, returnStdout: true).trim()
    }

    /**
     * 通过 ssh 密钥认证执行远程命令
     * @param privateKey 私钥内容（字符串）
     * @param host 远程主机
     * @param user 用户名
     * @param command 要执行的命令
     * @param steps pipeline steps（如 this）
     * @return 命令执行结果（stdout）
     */
    static String execWithKey(String host, String user, String privateKey, String command, steps) {
        def keyFile = '.tmp_ssh_key_' + UUID.randomUUID().toString().replaceAll('-', '')
        steps.writeFile file: keyFile, text: privateKey
        steps.sh "chmod 600 ${keyFile}"
        def sshCmd = "ssh -i ${keyFile} -o StrictHostKeyChecking=no ${user}@${host} '${command.replace("'", "'\\''")}'"
        LoggerUtils.info("[SSH-KEY] ${user}@${host} $command", steps)
        def result = steps.sh(script: sshCmd, returnStdout: true).trim()
        steps.sh "rm -f ${keyFile}"
        return result
    }
}