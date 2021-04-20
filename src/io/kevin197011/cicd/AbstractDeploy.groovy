package io.kevin197011.cicd

/**
 * class AbstractDeploy
 */

abstract class AbstractDeploy implements Gitable, Deployable {

    @Override
    abstract boolean gitClone(String repo)

    @Override
    abstract boolean gitPull(String path)

    @Override
    abstract boolean doDeploy()

    @Override
    abstract void validate()
}
