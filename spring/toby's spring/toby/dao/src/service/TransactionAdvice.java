package service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TransactionAdvice implements MethodInterceptor {
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        // DefaultTransactionDefinition -> 트랜잭션 정의를 통한 네 가지 조건

        try {
            Object ret = methodInvocation.proceed();
            this.transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e) { // -> 롤백 대상인 예외 종류
            this.transactionManager.rollback(status);
            throw e;
        }
        // 이러한 부분을 결합하여 트랜잭션 부가기능의 행동을 결정하는 TransactionAttribute 속성이 된다
    }
}
