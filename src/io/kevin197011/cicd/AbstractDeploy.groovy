package io.kevin197011.cicd

/**
 * class AbstractDeploy
 * 适用于 pipeline，所有核心方法带 steps 参数
 */
abstract class AbstractDeploy implements Gitable, Deployable {

    @Override
    abstract boolean gitClone(String repo, def steps)

    @Override
    abstract boolean gitPull(String path, def steps)

    @Override
    abstract boolean doDeploy(def steps)

    @Override
    abstract void validate()
}
