package io.kevin197011.cicd

/**
 * Deployable: 远程部署接口，适合 pipeline 场景
 */
interface Deployable {

    /**
     * 远程执行部署
     * @param steps pipeline steps（如 this）
     * @return 是否成功
     */
    boolean doDeploy(def steps)

    /**
     * 校验参数
     */
    void validate()
}