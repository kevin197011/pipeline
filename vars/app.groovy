#!/usr/bin/env groovy

def call() {

    def data = readYaml text: libraryResource('deploy/app.yml')

    //pipeline
    pipeline {
        // agent { node { label "Build"}}
        agent any

        stages {
            stage('test') {
                steps {
                    script {
                        println data
                    }
                }
            }
        }
    }
}
