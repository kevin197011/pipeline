package io.kevin197011.cicd


class LoggerUtils {

    /**
     * 输出 info 级别日志
     * @param message 日志内容
     * @param steps Jenkins Pipeline steps 对象（可选）
     */
    static void info(String message, steps = null) {
        if (steps) {
            steps.echo "[info] ${message}"
        } else {
            println "[info] ${message}"
        }
    }

    /**
     * 输出 warn 级别日志
     * @param message 日志内容
     * @param steps Jenkins Pipeline steps 对象（可选）
     */
    static void warn(String message, steps = null) {
        if (steps) {
            steps.echo "[warn] ${message}"
        } else {
            println "[warn] ${message}"
        }
    }

    /**
     * 输出 debug 级别日志
     * @param message 日志内容
     * @param steps Jenkins Pipeline steps 对象（可选）
     */
    static void debug(String message, steps = null) {
        if (steps) {
            steps.echo "[debug] ${message}"
        } else {
            println "[debug] ${message}"
        }
    }

    /**
     * 输出 error 级别日志
     * @param message 日志内容
     * @param steps Jenkins Pipeline steps 对象（可选）
     */
    static void error(String message, steps = null) {
        if (steps) {
            steps.echo "[error] ${message}"
        } else {
            println "[error] ${message}"
        }
    }
}
