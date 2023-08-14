import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

// 查看 Jenkins credentials 内置加密变量
// 一些内置加密变量调用方式 https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#string-interpolation:~:text=steps%20%7B%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20/*%20CORRECT%20*/-,sh,-(%27curl

def call() {
    def jenkinsCredentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
        com.cloudbees.plugins.credentials.Credentials.class,
        Jenkins.instance,
        null,
        null
)
    for (creds in jenkinsCredentials) {
        // println(creds.id)
        if (creds.id == '349f9a1e-b4a0-4f1a-98cf-b0574ccf1b54') {
            println(creds.username)
            println(creds.password)
        }
    }
}
