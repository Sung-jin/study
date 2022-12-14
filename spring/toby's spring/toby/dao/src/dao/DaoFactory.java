package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 해당 팩토리를 통해 어플리케이션의 컴포넌트 역할을 하는 오브젝트와
 * 어플리케이션의 구조를 결정하는 오브젝트를 분리한 것이 가장 큰 의의이다
 */
@Configuration
public class DaoFactory {
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
        // 위 메소드의 반환값만 변경하면, 해당 팩토리에 사용되는 DAO 들의 구현체를 한번에 변경이 가능해진다
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public Object someDao() {
        // return new SomeDao(connectionMaker());
        return null;
    }
}
