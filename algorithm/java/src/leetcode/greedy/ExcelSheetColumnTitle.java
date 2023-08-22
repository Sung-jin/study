package leetcode.greedy;

import java.util.*;

/*
Q.168 Excel Sheet Column Title
Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.
 */
public class ExcelSheetColumnTitle {
    private final static Map<Integer, Character> EXCEL_TITLE = new HashMap<>();

    static {
        EXCEL_TITLE.put(1, 'A');
        EXCEL_TITLE.put(2, 'B');
        EXCEL_TITLE.put(3, 'C');
        EXCEL_TITLE.put(4, 'D');
        EXCEL_TITLE.put(5, 'E');
        EXCEL_TITLE.put(6, 'F');
        EXCEL_TITLE.put(7, 'G');
        EXCEL_TITLE.put(8, 'H');
        EXCEL_TITLE.put(9, 'I');
        EXCEL_TITLE.put(10, 'J');
        EXCEL_TITLE.put(11, 'K');
        EXCEL_TITLE.put(12, 'L');
        EXCEL_TITLE.put(13, 'M');
        EXCEL_TITLE.put(14, 'N');
        EXCEL_TITLE.put(15, 'O');
        EXCEL_TITLE.put(16, 'P');
        EXCEL_TITLE.put(17, 'Q');
        EXCEL_TITLE.put(18, 'R');
        EXCEL_TITLE.put(19, 'S');
        EXCEL_TITLE.put(20, 'T');
        EXCEL_TITLE.put(21, 'U');
        EXCEL_TITLE.put(22, 'V');
        EXCEL_TITLE.put(23, 'W');
        EXCEL_TITLE.put(24, 'X');
        EXCEL_TITLE.put(25, 'Y');
        EXCEL_TITLE.put(26, 'Z');
    }

    public String convertToTitle(int columnNumber) {
        StringBuilder result = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        int remain = columnNumber;

        while(remain > 0) {
            numbers.add(remain % 26);
            remain /= 26;
        }

        for (int i = numbers.size() - 1; i >= 0; i--) {
            int index = numbers.get(i);
            result.append(EXCEL_TITLE.get(index));
        }

        return result.toString();
    }
}

/*
return n == 0 ? "" : convertToTitle(--n / 26) + (char)('A' + (n % 26));
 */