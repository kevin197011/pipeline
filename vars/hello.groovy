#!/usr/bin/env groovy

def call() {
    //load shareibrary
    def helloworld = new io.kevin197011.helloworld()

    //pipeline
    pipeline {
        // agent { node { label "build"}}
        agent any

        stages {

            stage('parameters') {
                steps {
                    script {
                        properties([
                        parameters([
                            choice(
                                choices: ['ONE', 'TWO'],
                                name: 'PARAMETER_01'
                            ),
                            booleanParam(
                                defaultValue: true,
                                description: 'SURE?',
                                name: 'BOOLEAN'
                            ),
                            text(
                                defaultValue: '''
                                this is a multi-line
                                string parameter example
                                ''',
                                name: 'MULTI-LINE-STRING'
                            ),
                            string(
                                defaultValue: 'user1',
                                name: 'username',
                                trim: true
                            )
                        ])
                    ])
                    }
                }
            }

            stage('test') {
                steps {
                    script {
                        helloworld.printMsg ${params.DEPLOY_ENV}
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
