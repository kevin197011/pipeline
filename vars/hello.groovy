#!/usr/bin/env groovy

def call(){

    //load shareibrary
    def helloworld = new io.kevin197011.helloworld()

    //pipeline
    pipeline {
        // agent { node { label "build"}}
        agent none

        stages{
            stage("test"){
                steps{
                    script{

                        helloworld.printMsg "devops-user"
                    }
                }
            }
        }
        post {
            always{
                script{
                    println("always")
                }
            }

            success{
                script{
                    println("success")

                }

            }
            failure{
                script{
                    println("failure")
                }
            }

            aborted{
                script{
                    println("aborted")
                }

            }

        }

    }

}
