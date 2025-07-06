// jenkinsfiles/job.Jenkinsfile
// Jenkinsfile 示例：通过 shared library 的 job.groovy 实现参数化作业管理，支持外部 DSL 和 Job 名称传递
// 需在 Jenkins 系统配置中注册 shared library

@Library('devops-lib') _

def jobName = env.JOB_NAME ?: 'test01'
def defaultJobDSL = """
@Library('devops-lib@${env.BRANCH ?: 'main'}') _

deploy {
    name = \"${jobName}\"
    version = \"${env.VERSION ?: 'main'}\"
    git = \"https://github.com/kevin197011/test\"
    host = \"localhost\"
}
"""

def jobDSL = env.JOB_DSL ?: defaultJobDSL

job(
    jobName: jobName,                 // Job 名称（可通过参数传递）
    branch: env.BRANCH ?: 'main',     // devops-lib 分支（可通过参数传递）
    version: env.VERSION ?: 'main',   // deploy.version（可通过参数传递）
    dsl: jobDSL                       // job dsl（可通过参数传递）
)

// 说明：
// - job.groovy 支持通过 jobName/branch/version/dsl 参数灵活控制
// - JOB_NAME/JOB_DSL 可通过流水线参数、Jenkinsfile 或调用时传递
// - 详细日志和错误会输出到 Jenkins 控制台