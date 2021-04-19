package io.kevin197011

import java.text.SimpleDateFormat

/**
 * Time class
 */
class Time {
    static String timeFormat() {
        return new SimpleDateFormat('MM/dd/yyyy HH:mm:ss').format(new Date()).toString()
    }
}
