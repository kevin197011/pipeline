#!/usr/bin/env groovy

import io.kevin197011.cicd.GitUtils
import io.kevin197011.cicd.DockerUtils
import io.kevin197011.cicd.LoggerUtils

def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent any

        parameters {
            string(name: 'GITLAB_REPO_URL', defaultValue: config.gitlabRepoUrl ?: '', description: 'GitLab 仓库地址')
            string(name: 'BRANCH', defaultValue: config.branch ?: 'master', description: '分支名')
            string(name: 'CREDENTIALS_ID', defaultValue: config.credentialsId ?: '', description: 'Jenkins Git 凭证ID')
            string(name: 'IMAGE_NAME', defaultValue: config.imageName ?: '', description: 'Docker 镜像名:tag')
            string(name: 'HARBOR_URL', defaultValue: config.harborUrl ?: '', description: 'Harbor 地址')
            string(name: 'HARBOR_CREDENTIALS_ID', defaultValue: config.harborCredentialsId ?: '', description: 'Jenkins Harbor 凭证ID')
        }

        stages {
            stage('参数校验') {
                steps {
                    script {
                        if (!params.GITLAB_REPO_URL) error 'GitLab 仓库地址不能为空'
                        if (!params.CREDENTIALS_ID) error 'Jenkins Git 凭证ID不能为空'
                        if (!params.IMAGE_NAME) error 'Docker 镜像名不能为空'
                        if (!params.HARBOR_URL) error 'Harbor 地址不能为空'
                        if (!params.HARBOR_CREDENTIALS_ID) error 'Harbor 凭证ID不能为空'
                    }
                }
            }

            stage('拉取代码') {
                steps {
                    script {
                        GitUtils.pull(this, params.GITLAB_REPO_URL, params.CREDENTIALS_ID, params.BRANCH)
                        LoggerUtils.info("Git 拉取完成", this)
                    }
                }
            }

            stage('构建镜像') {
                steps {
                    script {
                        DockerUtils.build(this, params.IMAGE_NAME)
                        LoggerUtils.info("镜像构建完成", this)
                    }
                }
            }

            stage('推送镜像') {
                steps {
                    script {
                        DockerUtils.push(this, params.IMAGE_NAME, params.HARBOR_URL, params.HARBOR_CREDENTIALS_ID)
                        LoggerUtils.info("镜像推送完成", this)
                    }
                }
            }
        }

        post {
            always {
                script {
                    LoggerUtils.info('流水线执行完毕', this)
                }
            }
            success {
                script {
                    LoggerUtils.info('流水线成功', this)
                }
            }
            failure {
                script {
                    LoggerUtils.info('流水线失败', this)
                }
            }
            aborted {
                script {
                    LoggerUtils.info('流水线中止', this)
                }
            }
        }
    }
}