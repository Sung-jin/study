package hello.springmvc.basic;

import lombok.Data;

@Data
// getter, setter, constructor 를 자동으로 생성해준다
public class HelloData {
    private String username;
    private int age;
}
