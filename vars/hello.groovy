#!/usr/bin/env groovy

import io.kevin197011.cicd.Message
import io.kevin197011.cicd.TimeUtils

def call() {

    //pipeline
    pipeline {
        // agent { node { label "Build"}}
        agent any

        parameters {
            string(name: 'username', defaultValue: 'user1', trim: true, description: 'username')
            booleanParam(name: 'Ok', defaultValue: true, description: 'ok?')
            choice(name: 'item', choices: ['A', 'B', 'C'], description: 'which one?')
        }

        stages {
            stage('test') {
                steps {
                    script {
                        println Message.getMsg(params.username)
                        println Message.booleanToString(params.Ok)
                        println params.item
                    }
                }
            }

            stage('parallel') {
                parallel {
                    stage('A') {
                        steps {
                            script {
                                printf('A %s\n', TimeUtils.timeFormat())
                                sleep(10)
                            }
                        }
                    }
                    stage('B') {
                        steps {
                            script {
                                printf('B %s\n', TimeUtils.timeFormat())
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
