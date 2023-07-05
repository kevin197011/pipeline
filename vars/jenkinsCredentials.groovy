import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

// 查看 Jenkins credentials 内置加密变量

def jenkinsCredentials = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
        com.cloudbees.plugins.credentials.Credentials.class,
        Jenkins.instance,
        null,
        null
)
for (creds in jenkinsCredentials) {
  // println(jenkinsCredentials.id)
  if (creds.id == '349f9a1e-b4a0-4f1a-98cf-b0574ccf1b54') {
    println(creds.username)
    println(creds.password)
  }
}
