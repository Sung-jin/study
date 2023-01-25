package service;

import domain.User;

public class UserServiceTx implements UserService {
    UserService userService; // 타깃 오브젝트
    PlatformTransactionManager transactionManager;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void add(User user) {
        // 메소드 구현과 위임
        userService.add(user);
    }

    @Override
    public void upgradeLevels() {
        // 메소드 구현
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        // 부가기능

        try {
            userService.upgradeLevels(); // 위임
            this.transactionManager.commit(status); // 부가기능
        } catch (RuntimeException e) {
            this.transactionManager.rollback(sstatus); // 부가기능
            throw e;
        }
    }
}
