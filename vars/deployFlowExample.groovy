// vars/deployFlowExample.groovy
// Jenkins Shared Library 示例：如何在 vars 下调用 DeployFlow.groovy
// 用法：在 Jenkinsfile 里直接调用 deployFlowExample()

def call(Map config = [:]) {
    def stagesConf = io.kevin197011.cicd.DeployFlow.getStages(config)
    pipeline {
        agent any
        parameters {
            string(name: 'appName', defaultValue: 'app', trim: true, description: 'appName')
            booleanParam(name: 'flag', defaultValue: false, description: 'sure?')
            choice(name: 'version', choices: ['A', 'B', 'C'], description: 'which version?')
        }
        stages {
            // 动态生成 stage
            stagesConf.each { stageConf ->
                stage(stageConf.name) {
                    steps {
                        script {
                            if (stageConf.steps instanceof Closure) {
                                stageConf.steps(this)
                            } else {
                                echo "Stage: ${stageConf.name}"
                            }
                        }
                    }
                }
            }
        }
        post {
            always {
                script {
                    io.kevin197011.cicd.DeployFlow.logInfo('always', this)
                }
            }
            success {
                script {
                    io.kevin197011.cicd.DeployFlow.logInfo('success', this)
                }
            }
            failure {
                script {
                    io.kevin197011.cicd.DeployFlow.logInfo('failure', this)
                }
            }
            aborted {
                script {
                    io.kevin197011.cicd.DeployFlow.logInfo('aborted', this)
                }
            }
        }
    }
}

// ---
// Jenkinsfile 示例：
// @Library('devops-lib') _
// deployFlowExample(env: 'prod', image: 'myapp:latest')