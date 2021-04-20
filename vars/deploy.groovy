#!/usr/bin/env groovy

import io.kevin197011.Deploy

def call(body) {

    def config = [
            name: "devops",
            age : "18",
            id  : "123456"
    ]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def gitRepo = "https://github.com/test"
    def hostIp = "https://github.com/test"

    def deploy = new Deploy(gitRepo, hostIp)


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
                        println(deploy.toString())
                        config.each {
                            printf("%s => %s\n", it.key, it.value)
                        }
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
