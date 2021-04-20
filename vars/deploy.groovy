#!/usr/bin/env groovy

import io.kevin197011.cicd.Deploy
import io.kevin197011.cicd.Message

def call(Closure body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

//    def projectGit = config.git
//    def projectHost = config.host

//    config.each { key, val ->
//        printf("%s => %s\n", key.toString(), val.toString())
//    }

    def deploy = new Deploy(config.git as String, config.host as String)


    //pipeline
    pipeline {
        // agent { node { label "build"}}
        agent any

        parameters {
            string(name: 'appName', defaultValue: 'app', trim: true, description: 'appName')
            booleanParam(name: 'flag', defaultValue: false, description: 'sure?')
            choice(name: 'version', choices: ['A', 'B', 'C'], description: 'which version?')
        }

        stages {
            stage("run?") {
                steps {
                    script {
                        if (!params.flag) {
                            error "not sure, break!"
                        }
                    }
                }
            }
            stage('test') {
                steps {
                    script {
                        println Message.getMsg(params.appName)
                        println Message.booleanToString(params.flag)
                        println params.version
                        println deploy.toString()
                    }
                }
            }

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
                    println('always')
                }
            }

            success {
                script {
                    println('success')
                }
            }

            failure {
                script {
                    println('failure')
                }
            }

            aborted {
                script {
                    println('aborted')
                }
            }
        }
    }
}
