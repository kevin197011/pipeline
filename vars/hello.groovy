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
            string(name: 'username', defaultValue: 'user1', trim: true, description: 'username')
            booleanParam(name: 'Ok', defaultValue: true, description: 'ok?')
            choice(name: 'item', choices: ['A', 'B', 'C'], description: 'which one?')
        }

        stages {
            stage('test') {
                steps {
                    script {
                        println Each.printMsg(params.username)
                        println Each.booleanToString(params.Ok)
                        println params.item
                    }
                }
            }

            stage('parallel') {
                parallel {
                    stage('A') {
                        steps {
                            script {
                                printf('A %s\n', Time.timeFormat())
                                sleep(10)
                            }
                        }
                    }
                    stage('B') {
                        steps {
                            script {
                                printf('B %s\n', Time.timeFormat())
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
