package com.example.understandingspringcoreprinciple.singleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {}
    // private 생성자를 지정하여, 외부에서 해당 객체를 만드는 것을 방지해야 한다

    public void logic() {
        System.out.println("싱글톤 객체 로직 실행");
    }
}

/*
1. static 영역에 객체 instance 를 미리 하나 생성해서 올려둔다
2. 해당 객체 인스턴스가 필요한 경우, 오직 getInstance() 메서드를 통해서만 조회할 수 있다
3. 1개의 객체 인스턴스만 존재해야 하므로, 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 방지하기 위해 private 생성자를 지정한다
 */