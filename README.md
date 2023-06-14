# pipeline

pipeline

https://github.com/jenkinsci/pipeline-examples

```bash
# jenkinsfile

@Library('devops-lib@main') _

hello()

```

```bash
# jenkinsfile

@Library('devops-lib@main') _

deploy {
    name = "app01"
    version = "main"
    git = "https://github.com/kevin197011/test"
    host = "localhost"
}

```
