package service;

import org.springframework.beans.factory.FactoryBean;
import proxy.TransactionHandler;

import java.lang.reflect.Proxy;

/**
 * 프록시를 적용할 경우, 적용할 대상이 구현하고 있는 인터페이스를 구현하는 프록시 클래스를 다 구현해야 하며,
 * 부가적인 기능이 여러 메소드에 반복적으로 나타나는 문제점이 존재한다
 *
 * 프록시 빈 팩토리는 이러한 문제점을 모두 해결해 준다
 * - 다이내믹 프록시를 이용하면 타깃 인터페이스를 모두 구현하는 작업을 제거할 수 있다
 * - 하나의 핸들러 메소드를 구현하여 많은 메소드에 부가기능을 제공할 수 있으므로, 중복 문제도 해결된다
 */
public class TxProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);

        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { serviceInterface },
                txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
