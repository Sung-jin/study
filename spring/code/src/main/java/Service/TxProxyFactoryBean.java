package Service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    Class<?> serviceInterface;
    // 다이내믹 프록시를 생성할 때 필요하다.
    // UserService 외의 인터페이스를 가진 타겟에도 적용할 수 있음


    public void setTarget(Object target) {
        this.target = target;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);

        return Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class[] { serviceInterface }, txHandler
        );
    }

    public Class<?> getObjectType() {
        return serviceInterface;
        // 팩토리 빈이 생성하는 오브젝트 타입은 DI 받은 인터페이스에 따라 달라지며, 다양한 타입의 프록시 오브젝트 생성에 재사용 할 수 있다.
    }

    public boolean isSingleton() {
        return false;
    }
}
