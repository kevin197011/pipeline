#!/usr/bin/env groovy

import io.kevin197011.cicd.TestUtils
import io.kevin197011.cicd.Build

def call() {
    def testUtils = new TestUtils()
    def build = new Build()

    //pipeline
    pipeline {
        // agent { node { label "Build"}}
        agent any

        options {
            timestamps()
            skipDefaultCheckout()
            disableConcurrentBuilds()
            timeout(time: 1, unit: 'HOURS')
        }

        parameters {
            base64File 'small'
//            stashedFile 'large'
        }

        stages {
            stage('test') {
                steps {
                    script {
                        build.Build()
                        testUtils.run()
                    }

                    withFileParameter('small') {
                        sh 'cat ${small}'
                    }

//                    unstash 'large'
//                    sh 'cat large'

                    sh 'ls -ltR'
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
