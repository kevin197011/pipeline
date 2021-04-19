#!/usr/bin/env groovy

def call() {
    //load shareibrary
    def helloworld = new io.kevin197011.helloworld()

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
                        helloworld.printMsg(params.username)
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
