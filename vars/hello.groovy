#!/usr/bin/env groovy

import io.kevin197011.Each
import io.kevin197011.Time


def call() {
    //load sharelibrary
    // def hello = new hello()
    // def time = new time()

    //pipeline
    pipeline {
        // agent { node { label "build"}}
        agent any

        parameters {
            string(defaultValue: 'user1', name: 'username', trim: true, description: 'username')
            booleanParam(defaultValue: false, name: 'isOk', description: 'sure?')
            choice(choices: ['A', 'B', 'C'], name: 'item', description: 'which one?')
        }

        stages {
            stage('test') {
                steps {
                    script {
                        println Each.printMsg(params.username)
                        println boolean.toString(params.isOK)
                        println params.item
                    }
                }
            }

            stage('parallel') {
                parallel {
                    stage('A') {
                        steps {
                            script {
                                printf('A %s', Time.timeFormat())
                                sleep(10)
                            }
                        }
                    }
                    stage('B') {
                        steps {
                            script {
                                printf('B %s', Time.timeFormat())
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
