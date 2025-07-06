package io.kevin197011.cicd

/**
 * Gitable interface
 */

interface Gitable {

    /**
     * 远程 clone 仓库
     * @param repo 仓库地址
     * @param steps pipeline steps（如 this）
     * @return 是否成功
     */
    boolean gitClone(String repo, def steps)

    /**
     * 远程 pull 仓库
     * @param path 仓库目录
     * @param steps pipeline steps（如 this）
     * @return 是否成功
     */
    boolean gitPull(String path, def steps)
}