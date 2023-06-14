#!/usr/bin/env groovy

def call() {

    //pipeline
    pipeline {
        // agent { node { label "Build"}}
        agent any

        stages {
            stage('test') {
                steps {
                    script {
                        def data = readYaml text: resource('deploy/app.yml')
                        println data
                    }
                }
            }
        }
    }
}
