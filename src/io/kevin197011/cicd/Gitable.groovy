package io.kevin197011.cicd

/**
 * Gitable interface
 */

interface Gitable {

    /**
     * 远程 clone 仓库
     * @param steps pipeline steps（如 this）
     * @return 是否成功
     */
    boolean gitClone(def steps)

    /**
     * 远程 pull 仓库
     * @param steps pipeline steps（如 this）
     * @return 是否成功
     */
    boolean gitPull(def steps)
}