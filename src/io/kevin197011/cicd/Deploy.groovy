package io.kevin197011.cicd

/**
 * Deploy class
 */

class Deploy extends AbstractDeploy {

    private String repo
    private String host

    Deploy(String repo, String host) {
        this.repo = repo
        this.host = host
    }

    @Override
    boolean gitClone(String repo) {
        return false
    }

    @Override
    boolean gitPull(String path) {
        return false
    }

    @Override
    boolean doDeploy() {
        return false
    }

    @Override
    void validate() {
        // TODO
    }

    @Override
    public String toString() {
        return "Deploy{" +
                "repo='" + repo + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
