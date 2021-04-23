package test.io.kevin197011.cicd


import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.junit.Test

class PipelineTest /*extends BasePipelineTest*/ {

    @Test
    void test01() {
        assert 1 == 1
        assert 1 == 2
    }

    @Test
    void test02() {
        def jobDSL = '''
@Library('devops-lib@master') _

hello()
'''
        def instance = Jenkins.getInstanceOrNull()
        if (instance == null) {
            throw new RuntimeException("instance is null, break!")
        }
        def flowDefinition = new CpsFlowDefinition(jobDSL, true)
        def job = new WorkflowJob(instance, "test01")
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
}
