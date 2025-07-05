package io.kevin197011.cicd

/**
* class DeployFlow
*/
// @CompileStatic
class DeployFlow {

    Closure apply(Map config) {
        return {
            //pipeline
            pipeline {
                // agent { node { label "Build"}}
                agent any

                parameters {
                    string(name: 'appName', defaultValue: 'app', trim: true, description: 'appName')
                    booleanParam(name: 'flag', defaultValue: false, description: 'sure?')
                    choice(name: 'version', choices: ['A', 'B', 'C'], description: 'which version?')
                }

                stages {
                    stage('do?') {
                        steps {
                            script {
                                if (!params.flag) {
                                    error 'Not sure, break!'
                                }
                            }
                        }
                    }

                    // stage test
                    stage('test') {
                        steps {
                            script {
                                if (config) {
                                    config.each {
                                        LoggerUtils.info("${it.key } => ${it.value}", this)
                                    }
                                }
                                // LoggerUtils.info(Message.getMsg(params.appName), this)
                                // LoggerUtils.info(Message.booleanToString(params.flag), this)
                                // LoggerUtils.info(params.version, this)
                                // LoggerUtils.info(deploy.toString(), this)
                            }
                        }
                    }

                    // stage parallel
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
                            LoggerUtils.info('always', this)
                        }
                    }

                    success {
                        script {
                            LoggerUtils.info('success', this)
                        }
                    }

                    failure {
                        script {
                            LoggerUtils.info('failure', this)
                        }
                    }

                    aborted {
                        script {
                            LoggerUtils.info('aborted', this)
                        }
                    }
                }
            }
        }
    }

}

