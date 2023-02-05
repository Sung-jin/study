package test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import proxy.Hello;
import proxy.HelloTarget;
import proxy.UppercaseHandler;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {

    @Test
    public void simpleProxy() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { Hello.class },
                new UppercaseHandler(new HelloTarget())
        );
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvice(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        // 특정 메소드에만 부가기능이 적용될 수 있도록 pointcut 을 활용할 수 있다

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        assertEquals(proxiedHello.sayThankYou("Toby"), "Thank You Toby");
    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String)methodInvocation.proceed();
            return ret.toUpperCase(); // 부가기능
        }
    }
}
