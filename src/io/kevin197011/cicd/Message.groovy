package io.kevin197011.cicd

/**
 * Message class
 */

class Message {
    static String getMsg(String name) {
        return sprintf("hello %s", name)
    }

    static String booleanToString(boolean b) {
        return String.valueOf(b)
    }
}
