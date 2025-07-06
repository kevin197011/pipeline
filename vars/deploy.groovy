#!/usr/bin/env groovy

import io.kevin197011.cicd.Deploy
import io.kevin197011.cicd.LoggerUtils

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def deploy = new Deploy(
        config.git as String,
        config.host as String,
        config.user as String,
        config.password,
        config.privateKey
    )

    //pipeline
    pipeline {
        // agent { node { label "Build"}}
        agent any

        parameters {
            string(name: 'appName', defaultValue: 'app', trim: true, description: 'appName')
            booleanParam(name: 'flag', defaultValue: false, description: 'sure?')
            choice(name: 'version', choices: ['A', 'B', 'C'], description: 'which version?')
        }

        stages {
            stage('do?') {
                steps {
                    script {
                        if (!params.flag) {
                            error 'Not sure, break!'
                        }
                    }
                }
            }

            stage('git clone') {
                steps {
                    script {
                        deploy.validate()
                        def ok = deploy.gitClone(this)
                        LoggerUtils.info("gitClone result: ${ok}", this)
                    }
                }
            }

            stage('git pull') {
                steps {
                    script {
                        def ok = deploy.gitPull(this)
                        LoggerUtils.info("gitPull result: ${ok}", this)
                    }
                }
            }

            stage('deploy') {
                steps {
                    script {
                        def ok = deploy.doDeploy(this)
                        LoggerUtils.info("doDeploy result: ${ok}", this)
                    }
                }
            }

            // stage test
            stage('test') {
                steps {
                    script {
                        config.each {
                            LoggerUtils.info("${it.key } => ${it.value}", this)
                        }
                        LoggerUtils.info(deploy.toString(), this)
                    }
                }
            }

            // stage parallel
            stage('parallel') {
                parallel {
                    stage('A') {
                        steps {
                            script {
                                sleep(10)
                            }
                        }
                    }
                    stage('B') {
                        steps {
                            script {
                                sleep(10)
                            }
                        }
                    }
                }
            }
        }

        post {
            always {
                script {
                    LoggerUtils.info('always', this)
                }
            }

            success {
                script {
                    LoggerUtils.info('success', this)
                }
            }

            failure {
                script {
                    LoggerUtils.info('failure', this)
                }
            }

            aborted {
                script {
                    LoggerUtils.info('aborted', this)
                }
            }
        }
    }
}
