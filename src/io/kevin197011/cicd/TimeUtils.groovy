package io.kevin197011.cicd

import java.text.SimpleDateFormat

/**
 * TimeUtils class
 */

class TimeUtils {
    static String timeFormat() {
        return new SimpleDateFormat('MM/dd/yyyy HH:mm:ss').format(new Date()).toString()
    }
}
