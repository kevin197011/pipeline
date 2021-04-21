#!/usr/bin/env groovy

import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob

def call() {
    def jobName = "test01"
    def jobDSL = '''
@Library('devops-lib@master') _

hello()
'''
    def instance = Jenkins.getInstanceOrNull()
    if (instance == null) {
        error "Instance is Null!"
    }
    def flowDefinition = new CpsFlowDefinition(jobDSL, true)
    def job = new WorkflowJob(instance, jobName)
    job.definition = flowDefinition
    job.setConcurrentBuild(false)

//        job.addProperty(new RateLimitBranchProperty.JobPropertyImpl
//                (new RateLimitBranchProperty.Throttle(60, "hours")))
//        def spec = "H 0 1 * *"
//        TimerTrigger newCron = new TimerTrigger(spec)
//        newCron.start(job, true)
//        job.addTrigger(newCron)
    job.save()
    Jenkins.getInstanceOrNull().reload()
}