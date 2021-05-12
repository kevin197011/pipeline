package test.io.kevin197011.cicd

import io.kevin197011.cicd.Deploy
import io.kevin197011.cicd.LoggerUtils
import jenkins.mvn.GlobalMavenConfig
import org.junit.Test

import static groovy.test.GroovyAssert.shouldFail

class JenkinsTest {

    @Test
    void test01() {
        def numbers = [1, 2, 3, 4]
        shouldFail {
            numbers.get(4)
        }
    }

    @Test
    void test02() {
        Deploy deploy = new Deploy("https://github.com/kevin197011/test", "localhost")
        LoggerUtils.info deploy.toString()
        LoggerUtils.info "hello world!"
        LoggerUtils.warn "hello world!"
        LoggerUtils.debug "hello world!"
        LoggerUtils.error "hello world!"
        assert "Deploy{repo='https://github.com/kevin197011/test', host='localhost'}" == deploy.toString()
    }

    @Test
    void test03(){
        hudson.model.JDK.all()
    }
}
