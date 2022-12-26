package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class JunitTest {

    @Autowired
    ApplicationContext context;
    static Set<JunitTest> junitTest = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test
    public void test1() {
        junitTest.forEach(obj -> assertNotSame(this, obj));
        assertNotSame(contextObject, context);
        assertTrue(contextObject == null || contextObject == context);

        junitTest.add(this);
        contextObject = this.context;
    }

    @Test
    public void test2() {
        assertNotSame(contextObject, context);
        assertTrue(contextObject == null || contextObject == context);

        junitTest.add(this);
        contextObject = this.context;
    }

    @Test
    public void test3() {
        assertNotSame(contextObject, context);
        assertTrue(contextObject == null || contextObject == context);

        junitTest.add(this);
        contextObject = this.context;
    }
}
