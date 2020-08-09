package Calc;

/*
테스트와 리펙토링

요구사항
문자열 계산기를 만들어라.

문자열 계산기란?
전달하는 문자를 구분자로 분리한 후 각 숫자의 합을 구해 반환하는 계산기이다.

ex)
요구사항 1
, : 을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 각 숫자의 합을 반환한다.
"" -> 0
"1,2" -> 3
"1,2,3" -> 6
"1,2:3" -> 6

요구사항 2
기본 구분자를 제외하고 커스텀 구분자를 지정할 수 있으며, // 와 \n 사이의 문자를 커스텀 구분자로 사용한다.
"//;\n1;2;3" -> ; 는 커스텀 구분자가 되며, 1 + 2 + 3 인 6이 반환되어야 한다.

구현 후 내용을 읽는데, 커스텀일 경우에는 기본 구분 연산자를 사용하지 않고, 커스텀 연산자만 사용하는 형태인데,
이건 기능을 정의하기 나름일 것 같다.
기존 연산자에 추가로 붙이는 형태인지, 덮어 씌우는 형태인지.

요구사항 3
음수를 전달하는 경우 RuntimeException 으로 예외처리 해야 한다.
*/

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringCalculator {
    private String formula;

    public StringCalculator(String formula) {
        if (formula == null || formula.isEmpty()) formula = "0";
        if (formula.contains("-")) throw new RuntimeException();

        this.formula = formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int add() throws NumberFormatException {
        String operatorRegex = getOperatorRegexByFormula(formula);
        int endCustomOperatorIndex = formula.lastIndexOf("\n") > 0 ? formula.lastIndexOf("\n") + 1 : 0;

        return Stream.of(formula
                .substring(endCustomOperatorIndex)
                .split(operatorRegex)
        ).mapToInt(Integer::parseInt)
                .sum();
    }

    private String getOperatorRegexByFormula(String formula) {
        ArrayList<String> operator = new ArrayList<String>() {{
            add(",");
            add(":");
        }};
        Matcher m = Pattern.compile(
                Pattern.quote("//")
                        + "(.*?)"
                        + Pattern.quote("\n")
        ).matcher(formula);

        while(m.find()){
            operator.add(m.group(1));
        }

        return "\\" + String.join("|\\", operator);
    }

}
