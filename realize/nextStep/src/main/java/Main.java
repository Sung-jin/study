import Calc.Calculator;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        System.out.println(calculator.add(3, 4));
        System.out.println(calculator.sub(10, 2));
        System.out.println(calculator.mul(1, 3));
        System.out.println(calculator.div(4, 2));
    }
}
