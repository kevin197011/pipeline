# pipeline

pipeline

https://github.com/jenkinsci/pipeline-examples

```bash
# jenkinsfile

@Library('devops-lib@master') _

hello()

```

```bash
# jenkinsfile

@Library('devops-lib@master') _

deploy {
    name = "app01"
    version = "master"
    git = "https://github.com/kevin197011/test"
    host = "localhost"
}

```