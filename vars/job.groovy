#!/usr/bin/env groovy

import io.kevin197011.cicd.JenkinsJob

def call() {

    // set new add job name
    def jobName = "test01"
    // config pipeline dsl syntax
    def jobDSL = '''
@Library('devops-lib@master') _

hello()
'''
    def job = new JenkinsJob(jobName, jobDSL)

    // pipeline
    pipeline {
        
        agent any

        stages {
            stage('add job') {
                steps {
                    script {
                        if (!job.addJob()) {
                            println "${jobName} add failure!"
                        }
                        println "${jobName} add success!"
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

