
package io.kevin197011

import java.text.SimpleDateFormat

def timeFmt() {
    def date = new Date()
    def sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss')
    return sdf.format(date)
}
