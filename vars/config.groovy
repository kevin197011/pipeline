#!/usr/bin/env groovy

def call() {
    def data = readYaml text: libraryResource('config/app.yml')

    //pipeline
    pipeline {
        agent any

        environment {
            SERVER_KEY = credentials('349f9a1e-b4a0-4f1a-98cf-b0574ccf1b54')
        }

        stages {
            stage('test') {
                steps {
                    script {
                        println data
                    // println $SERVER_KEY
                    // println $SERVER_KEY_USR
                    }

                    sh 'echo $SERVER_KEY '
                    sh 'echo $SERVER_KEY_USR'
                }
            }
        }
    }
}
