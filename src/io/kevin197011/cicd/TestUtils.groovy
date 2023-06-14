package io.kevin197011.cicd

@Grab(group='org.apache.commons', module='commons-lang3', version='3.12.0')
import org.apache.commons.lang3.StringUtils

class TestUtils {
    int tries = 0
    def run() {
        println("test utils!")
        while (tries < 10) {
            Thread.sleep(1000)
            tries++
            println("tries is numeric: " + StringUtils.isAlphanumeric("." + tries))
        }
    }
}
