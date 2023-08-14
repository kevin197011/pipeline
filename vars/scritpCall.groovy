#!/usr/bin/env groovy
// Copyright (c) 2023 kk
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

def call() {
    def t1SH = libraryResource('script/t1.sh')

    //pipeline
    pipeline {
        agent any

        stages {
            stage('do?') {
                steps {
                    script {
                        sh t1SH
                    }
                }
            }
        }
    }
}
