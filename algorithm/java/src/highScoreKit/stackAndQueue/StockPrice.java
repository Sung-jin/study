package highScoreKit.stackAndQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.

제한사항
prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
prices의 길이는 2 이상 100,000 이하입니다.
 */
public class StockPrice {
    public int[] solution(int[] prices) {
        int[] result = new int[prices.length];

        if (prices.length == 0) return result;

        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[i] > prices[j]) {
                    result[i] = j - i;
                    break;
                }
                if (j == prices.length - 1) {
                    result[i] = j - i;
                }
            }
        }

        result[result.length - 1] = 0;

        return result;
    }
}

/*
// 자료구조/알고리즘: 스택
// 시간 복잡도: O(n^2)
// 공간 복잡도: O(n)

class Solution {
    public int[] solution(int[] prices) {
        int n = prices.length;
        int[] answer = new int[n];
        Stack<PriceEntry> priceHistory = new Stack<>();

        for (int i = 0; i < n; i++) {
            int price = prices[i];
            while (!priceHistory.isEmpty() && price < priceHistory.peek().price) {
                PriceEntry priceEntry = priceHistory.pop();
                answer[priceEntry.index] = i - priceEntry.index;
            }
            priceHistory.push(new PriceEntry(i, price));
        }

        while (!priceHistory.isEmpty()) {
            PriceEntry priceEntry = priceHistory.pop();
            answer[priceEntry.index] = n - priceEntry.index - 1;
        }

        return answer;
    }

    class PriceEntry {
        int index;
        int price;

        public PriceEntry(int index, int price) {
            this.index = index;
            this.price = price;
        }
    }
}
 */
