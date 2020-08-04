package Calc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {
    private Calculator cal;

    @Before
    public void before() {
        cal = new Calculator();
    }
    // Before : 테스트 함수마다 동작
    // BeforeClass : 테스트 마다가 아닌, 클래스 단위로 맨 처음 한번만 실행
    // 공통으로 사용하는 값을 초기화를 따로 분리하는 이유는 각 테스트가 이전 값에 의해 영향을 받을 수 있기 때문
    // 또한, before 를 사용하면 @RunWith, @Rule 에서 초기화된 객체에 접근할 수 있는 제약조건이 존재한다.
    // After, AfterClass 도 존재하며 이는 모든 테스트가 종료 또는, 테스트 종료 마다 실행된다.

    @Test
    public void add() {
        assertEquals(9, cal.add(6, 3));
    }

    @Test
    public void sub() {
        assertEquals(3, cal.sub(6, 3));
    }

    @Test
    public void mul() {
        assertEquals(18, cal.mul(6, 3));
    }

    @Test
    public void div() {
        assertEquals(2, cal.div(6, 3));
    }
}
