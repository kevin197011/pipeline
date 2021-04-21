package io.kevin197011.cicd


class LoggerUtils {

    static void info(String message) {
        println "[info] ${message}"
    }

    static void warn(String message) {
        println "[warn] ${message}"
    }

    static void debug(String message) {
        println "[debug] ${message}"
    }

    static void error(String message) {
        println "[error] ${message}"
    }
}
