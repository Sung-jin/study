package DAO;

import Config.studyConnectionMaker;
import Config.SimpleConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의한 설계도와 같은 역할
* 어떤 오브젝트가 어떤 오브젝트를 사용할지를 정해놓은 코드
* */

@Configuration
public class DaoFactory {

    @Bean
    public UserDAO studyUserDao() {
        return new UserDAO(StudyConnectionMaker());
    }

    private SimpleConnectionMaker StudyConnectionMaker() {
        return new studyConnectionMaker();
    }
}
