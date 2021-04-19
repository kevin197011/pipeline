package io.kevin197011

class Deploy {

    private String gitRepo
    private String hostIp

    Deploy(String gitRepo, String hostIp) {
        this.gitRepo = gitRepo
        this.hostIp = hostIp
    }

    @Override
    public String toString() {
        return "Deploy{" +
                "gitRepo='" + gitRepo + '\'' +
                ", hostIp='" + hostIp + '\'' +
                '}';
    }
}
