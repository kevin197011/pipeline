import io.kevin197011.cicd.HarborUtils
import io.kevin197011.cicd.LoggerUtils

def call(Map config = [:]) {
    pipeline {
        agent any

        parameters {
            string(name: 'HARBOR_REPO', defaultValue: config.repo ?: 'harbor.example.com/project/app', description: 'Harbor 镜像仓库')
            string(name: 'HARBOR_CRED', defaultValue: config.credId ?: '', description: 'Jenkins凭据ID（用户名密码）')
        }

        stages {
            stage('获取 Harbor 镜像版本') {
                steps {
                    script {
                        def repo = params.HARBOR_REPO
                        def credId = params.HARBOR_CRED
                        def repoParts = repo.split('/')
                        if (repoParts.size() < 3) {
                            error "HARBOR_REPO 格式应为 harbor.example.com/project/app"
                        }
                        def harborHost = repoParts[0]
                        def project = repoParts[1]
                        def app = repoParts[2]
                        def tags = []
                        if (credId) {
                            withCredentials([usernamePassword(credentialsId: credId, usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASS')]) {
                                tags = HarborUtils.getTags(harborHost, project, app, env.HARBOR_USER, env.HARBOR_PASS)
                            }
                        } else {
                            tags = HarborUtils.getTags(harborHost, project, app)
                        }
                        env.SELECTED_TAG = input(
                            id: 'tagInput',
                            message: "请选择 Harbor 镜像版本",
                            parameters: [
                                [$class: 'ChoiceParameterDefinition', choices: tags.join('\n'), description: 'Harbor 镜像 tag', name: 'IMAGE_TAG']
                            ]
                        )
                        LoggerUtils.info("用户选择的镜像 tag: ${env.SELECTED_TAG}", this)
                    }
                }
            }

            stage('K8s 部署') {
                steps {
                    script {
                        def image = "${params.HARBOR_REPO}:${env.SELECTED_TAG}"
                        LoggerUtils.info("将部署镜像: ${image}", this)
                        // 这里可以调用 kubectl 或 helm 部署
                        // sh "kubectl set image deployment/app app=${image} -n your-namespace"
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