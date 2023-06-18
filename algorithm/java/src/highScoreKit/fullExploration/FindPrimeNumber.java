package highScoreKit.fullExploration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FindPrimeNumber {
    public int solution(String numbers) {
        Set<Integer> allNumbers = new HashSet<>();

        createNumbers(allNumbers, "", numbers);

        return filterPrimeNumbers(allNumbers).size();
    }

    private void createNumbers(Set<Integer> numbers, String createdNumber, String remainNumbers) {
        String[] splitNumbers = remainNumbers.split("");

        for (int i = 0; i < splitNumbers.length; i++) {
            String newCreateNumber = createdNumber + splitNumbers[i];
            numbers.add(Integer.parseInt(newCreateNumber));

            StringBuilder newRemainNumbers = new StringBuilder();

            for (int j = 0; j < splitNumbers.length; j++) {
                if (i != j) newRemainNumbers.append(splitNumbers[j]);
            }

            if (newRemainNumbers.length() != 0) {
                createNumbers(numbers, newCreateNumber, newRemainNumbers.toString());
            }
        }
    }

    private Set<Integer> filterPrimeNumbers(Set<Integer> numbers) {
        return numbers.stream()
                .filter(FindPrimeNumber::checkPrimeNumber)
                .collect(Collectors.toSet());
    }

    private static boolean checkPrimeNumber(Integer number) {
        if (Arrays.asList(0, 1).contains(number)) return false;
        else if (Arrays.asList(2, 3).contains(number)) return true;

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) return false;
        }

        return true;
    }
}
