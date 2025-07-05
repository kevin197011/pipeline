package io.kevin197011.cicd

/**
* class DeployFlow
*/
// @CompileStatic
class DeployFlow {

    /**
     * 输出 config 的所有 key/value 日志
     */
    static void logConfig(Map config, steps) {
        if (config) {
            config.each { k, v ->
                LoggerUtils.info("${k} => ${v}", steps)
            }
        }
    }

    /**
     * 输出 info 日志
     */
    static void logInfo(String message, steps) {
        LoggerUtils.info(message, steps)
    }

    /**
     * 根据 config 返回 stage 配置
     * @param config 传入参数
     * @return List<Map>，每个 map 包含 name、steps 等
     */
    static List<Map> getStages(Map config) {
        if (config?.stages) {
            return config.stages
        }
        return [
            [name: 'Build',  steps: { steps -> steps.echo 'Building...' }],
            [name: 'Test',   steps: { steps -> steps.echo 'Testing...' }],
            [name: 'Deploy', steps: { steps -> steps.echo 'Deploying...' }]
        ]
    }

    // 可扩展更多工具方法
}

