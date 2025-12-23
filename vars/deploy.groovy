#!/usr/bin/env groovy

import io.kevin197011.cicd.Deploy
import io.kevin197011.cicd.LoggerUtils

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def deploy = new Deploy(
        config.git as String,
        config.host as String,
        config.user as String,
        config.password,
        config.privateKey
    )

    // --- 动态生成参数 ---
    // 这里可以从文件、Git标签或者共享库获取
    def versions = ['A','B','C','D'] // 可以替换为动态获取逻辑

    // 使用 properties() 动态刷新参数
    properties([
        parameters([
            string(name: 'appName', defaultValue: 'app', trim: true, description: 'appName'),
            booleanParam(name: 'flag', defaultValue: false, description: 'sure?'),
            choice(name: 'version', choices: versions, description: 'which version?')
        ])
    ])

    // --- pipeline ---
    pipeline {
        agent any

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

            stage('git clone') {
                steps {
                    script {
                        deploy.validate()
                        def ok = deploy.gitClone(this)
                        LoggerUtils.info("gitClone result: ${ok}", this)
                    }
                }
            }

            stage('git pull') {
                steps {
                    script {
                        def ok = deploy.gitPull(this)
                        LoggerUtils.info("gitPull result: ${ok}", this)
                    }
                }
            }

            stage('deploy') {
                steps {
                    script {
                        def ok = deploy.doDeploy(this)
                        LoggerUtils.info("doDeploy result: ${ok}", this)
                    }
                }
            }

            stage('test') {
                steps {
                    script {
                        config.each {
                            LoggerUtils.info("${it.key } => ${it.value}", this)
                        }
                        LoggerUtils.info(deploy.toString(), this)
                    }
                }
            }

            stage('parallel') {
                parallel {
                    stage('A') {
                        steps { script { sleep(10) } }
                    }
                    stage('B') {
                        steps { script { sleep(10) } }
                    }
                }
            }
        }

        post {
            always { script { LoggerUtils.info('always', this) } }
            success { script { LoggerUtils.info('success', this) } }
            failure { script { LoggerUtils.info('failure', this) } }
            aborted { script { LoggerUtils.info('aborted', this) } }
        }
    }
}
