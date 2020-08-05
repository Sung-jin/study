package Calc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {
    private StringCalculator strCal;

    @Before
    public void before() {
        strCal = new StringCalculator();
    }

    // 요구사항 1
    @Test
    public void correctCalculateByDefaultSeparator() {
//        assertEquals(strCal.add("1,2"), 3);
//        assertEquals(strCal.add("1,2,3"), 6);
//        assertEquals(strCal.add("1,2:3"), 6);
    }

    // 요구사항 2
    @Test
    public void correctCalculateWithCustomSeparator() {
//        assertEquals(strCal.add("\\?\n4?5"), 9);
//        assertEquals(strCal.add("\\+\n4+5,6"), 15);
        assertEquals(strCal.add("//>\n//^\n4^5>6"), 15);
    }

    // 요구사항 3
    @Test(expected = RuntimeException.class)
    public void exceptionByNegativeOperator() {
        assertEquals(strCal.add("//>\n4-5>6"), 15);
    }
}
