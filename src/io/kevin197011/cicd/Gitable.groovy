package io.kevin197011.cicd

/**
 * Gitable interface
 */

interface Gitable {

    boolean gitClone(String repo)

    boolean gitPull(String path)

}