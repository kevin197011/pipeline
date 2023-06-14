#!/usr/bin/env groovy

import io.kevin197011.cicd.Deploy
// import io.kevin197011.cicd.DeployFlow

//import io.kevin197011.cicd.Message

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def projectGit = config.git
    def projectHost = config.host

    def deploy = new Deploy(config.git as String, config.host as String)

    // def df = new DeployFlow().apply(config)
    // df.call()

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

            // stage test
            stage('test') {
                steps {
                    script {
                        config.each {
                            println "${it.key } => ${it.value}"
                        }
                    //                        println Message.getMsg(params.appName)
                    //                        println Message.booleanToString(params.flag)
                    //                        println params.version
                    //                        println deploy.toString()

                        println deploy.toString()
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
