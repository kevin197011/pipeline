package test.io.kevin197011.cicd;

import jenkins.model.Jenkins;
import org.junit.Test;


/**
 * @author norton
 */
public class JavaTest {

    @Test
    public void test01() {
        Jenkins instance = Jenkins.getInstanceOrNull();
        System.out.println(instance.getDescription());
    }
}

