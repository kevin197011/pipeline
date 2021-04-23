#!/usr/bin/env groovy

import io.kevin197011.cicd.JenkinsJob

def call() {

    // set new add job name
    def jobName = "test01"
    // config pipeline dsl syntax
    def jobDSL = '''
@Library('devops-lib@master') _

deploy {
    name = "app01"
    version = "master"
    git = "https://github.com/kevin197011/test"
    host = "localhost"
}
'''
    def job = new JenkinsJob(jobName, jobDSL)

    // pipeline
    pipeline {

        agent any

        parameters {
            booleanParam(name: 'do', defaultValue: false, description: 'do?')
        }

        stages {

            stage("do?") {
                steps {
                    script {
                        if (!params.do) {
                            error "Not sure, break!"
                        }
                    }
                }
            }

            stage('add job') {
                steps {
                    script {
                        if (!job.addOrUpdateJob()) {
                            println "${jobName} add failure!"
                        }
                        println "${jobName} add success!"
                    }
                }
            }
            stage('delete job') {
                steps {
                    script {
                        if (!job.deleteJob()) {
                            println "${jobName} delete failure!"
                        }
                        println "${jobName} delete success!"
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

