package Calc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {
    // 요구사항 1
    @Test
    public void correctCalculateByDefaultSeparator() {
        StringCalculator defaultOperatorStringCalc1 = new StringCalculator("1,2");
        StringCalculator defaultOperatorStringCalc2 = new StringCalculator("1,2,3");
        StringCalculator defaultOperatorStringCalc3 = new StringCalculator("1,2:3");

        assertEquals(defaultOperatorStringCalc1.add(), 3);
        assertEquals(defaultOperatorStringCalc2.add(), 6);
        assertEquals(defaultOperatorStringCalc3.add(), 6);
    }

    // 요구사항 2
    @Test
    public void correctCalculateWithCustomSeparator() {
        StringCalculator customOperatorStringCalc1 = new StringCalculator("//?\n4?5");
        StringCalculator customOperatorStringCalc2 = new StringCalculator("//+\n4+5,6");
        StringCalculator customOperatorStringCalc3 = new StringCalculator("//>\n//^\n4^5>6");

        assertEquals(customOperatorStringCalc1.add(), 9);
        assertEquals(customOperatorStringCalc2.add(), 15);
        assertEquals(customOperatorStringCalc3.add(), 15);
    }

    // 요구사항 3
    @Test(expected = RuntimeException.class)
    public void exceptionByNegativeOperator() {
        StringCalculator runtimeExceptionWithMinusOperatorStringCalc = new StringCalculator("//>\n4-5>6");
    }

    //////////////////////////////////////////////////////////////////////
    // 추가 검증

    @Test
    public void inputByEmptyOrNull() {
        StringCalculator emptyOperatorStringCalc = new StringCalculator("");
        StringCalculator nullOperatorStringCalc = new StringCalculator(null);

        assertEquals(emptyOperatorStringCalc.add(), 0);
        assertEquals(nullOperatorStringCalc.add(), 0);
    }

    @Test
    public void inputWithoutOperator() {
        StringCalculator withoutOperatorStringCalc1 = new StringCalculator("3");
        StringCalculator withoutOperatorStringCalc2 = new StringCalculator("10");
        StringCalculator withoutOperatorStringCalc3 = new StringCalculator("22");

        assertEquals(withoutOperatorStringCalc1.add(), 3);
        assertEquals(withoutOperatorStringCalc2.add(), 10);
        assertEquals(withoutOperatorStringCalc3.add(), 22);
    }

    @Test(expected = NumberFormatException.class)
    public void noCustomOperatorTest() {
        StringCalculator customOperatorStringCalc1 = new StringCalculator("//?\n4&5");

        customOperatorStringCalc1.add();
    }
}
