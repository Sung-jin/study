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
 *
 * 위와 같은 편리함과 장점이 있지만, 다음과 같은 문제점이 존재한다
 * - 프록시를 통해 타깃에 부가기능을 제공하는 것은 메소드 단위로 발생하는 일이며, 여러개의 클래스에 공통적인 부가기능을 제공하는 일은 아래와 같은 형태로는 불가능하다
 *   - 즉, 거의 비슷한 프록시 팩토리 빈의 설정이 중복되는 것을 막을 수 없다
 * - 또한, 하나의 타깃에 여러 부가기능을 제공할 경우에 적용 대상 클래스 마다의 빈 설정이 추가되어야 하며, 이는 많은 클래스가 적용되어 있다면 가독성에도 문제가 발생한다
 *   - 즉, 적용할 클래스가 많아지고 적용할 기능이 많아질 수록 설정의 복잡성은 기하급수적으로 늘어나고, 관리하기 어려워 질 수 있다
 * - 타깃과 인터페이스만 다른 비슷한 설정이 반복되고, 프록시 팩토리 개수만큼 핸들러도 생성된다
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
