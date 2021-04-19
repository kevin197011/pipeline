#!/usr/bin/env groovy

import io.kevin197011.hello
import io.kevin197011.time

def call() {
    //load sharelibrary
    def hello = new hello()
    def time = new time()

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
                        hello.printMsg(params.username)
                    }
                }
            }

            stage('parallel') {
                parallel {
                    stage('A') {
                        steps {
                            script {
                                printf('A %s', time.timeFmt())
                                sleep(3000)
                            }
                        }
                    }
                    stage('B') {
                        steps {
                            script {
                                printf('B %s', time.timeFmt())
                                sleep(3000)
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
