package DAO;

import Config.AConnectionMaker;
import Config.BConnectionMaker;
import Config.SimpleConnectionMaker;

/*
* 애플리케이션을 구성하는 컴포넌트의 구조와 관계를 정의한 설계도와 같은 역할
* 어떤 오브젝트가 어떤 오브젝트를 사용할지를 정해놓은 코드
* */

public class DaoFactory {
    public UserDAO aUserDao() {
        return new UserDAO(aConnectionMaker());
    }

    public UserDAO bUserDao() {
        return  new UserDAO(bConnectionMaker());
    }

    private SimpleConnectionMaker aConnectionMaker() {
        return new AConnectionMaker();
    }

    private SimpleConnectionMaker bConnectionMaker() {
        return new BConnectionMaker();
    }
}
