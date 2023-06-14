package test.io.kevin197011.cicd

class groovyTest {

    static void main(String[] args) {
        def nums = [1, 2, 3, 4, 5, 6]
        //        println("nums is ${nums.getClass().getName()} size = ${nums.size()}")
        nums.each {
            println(it)
        }

        def colors = [red:'#FF0000', green:'#00FF00', blue:'#0000FF']

        colors.each {
            println("${it.key} :${it.value}")
        }
    }

}
