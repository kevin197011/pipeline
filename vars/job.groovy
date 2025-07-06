#!/usr/bin/env groovy

import io.kevin197011.cicd.JenkinsJob
import io.kevin197011.cicd.LoggerUtils

def call(Map config = [:]) {
    // 获取外部传参或默认值
    def branch = config.branch ?: 'main'
    def version = config.version ?: 'main'
    def jobName = config.jobName ?: 'test01'
    def defaultJobDSL = """
@Library('devops-lib@${branch}') _

deploy {
    name = \"${jobName}\"
    version = \"${version}\"
    git = \"https://github.com/kevin197011/test\"
    host = \"localhost\"
}
"""
    def jobDSL = config.dsl ?: defaultJobDSL
    def job = new JenkinsJob(jobName, jobDSL)

    // pipeline
    pipeline {
        agent any

        parameters {
            booleanParam(name: 'do', defaultValue: false, description: 'do?')
            string(name: 'JOB_NAME', defaultValue: jobName, description: 'Job 名称')
            string(name: 'BRANCH', defaultValue: branch, description: 'devops-lib 分支')
            string(name: 'VERSION', defaultValue: version, description: 'deploy.version')
            text(name: 'JOB_DSL', defaultValue: jobDSL, description: '自定义 Job DSL（可选）')
        }

        stages {
            stage('do?') {
                steps {
                    script {
                        if (!params.do) {
                            error 'Not sure, break!'
                        }
                    }
                }
            }

            stage('add job') {
                steps {
                    script {
                        // 优先用参数 JOB_DSL，否则用默认模板
                        def dynamicJobDSL = params.JOB_DSL?.trim() ? params.JOB_DSL : """
@Library('devops-lib@${params.BRANCH}') _

deploy {
    name = \"${params.JOB_NAME}\"
    version = \"${params.VERSION}\"
    git = \"https://github.com/kevin197011/test\"
    host = \"localhost\"
}
"""
                        job.name = params.JOB_NAME
                        job.dsl = dynamicJobDSL
                        if (!job.addOrUpdateJob()) {
                            error "${params.JOB_NAME} add failure!"
                        }
                        LoggerUtils.info("${params.JOB_NAME} add success!", this)
                    }
                }
            }
            stage('delete job') {
                steps {
                    script {
                        job.name = params.JOB_NAME
                        if (!job.deleteJob()) {
                            error "${params.JOB_NAME} delete failure!"
                        }
                        LoggerUtils.info("${params.JOB_NAME} delete success!", this)
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

