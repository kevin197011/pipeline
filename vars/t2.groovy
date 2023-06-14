// Copyright (c) 2023 kk
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

def call() {
    pipeline {
        agent any
        parameters {
            extendedChoice multiSelectDelimiter: ',', name: 'namespaces', quoteValue: false, saveJSONParameterToFile: false, type: 'PT_CHECKBOX', value: 'a,b,c', visibleItemCount: 3
        }
        stages {
            stage('test') {
                steps {
                    script {
                        println(params.namespaces)
                        params.namespaces.split(',').each {
                            sh """
                                echo "${it}"
                            """
                        }
                    }
                }
            }
        }

        post {
            always {
                script {
                    println('finished!')
                }
            }
        }
    }
}
