package leetcode.dfs;

import java.util.HashSet;
import java.util.Set;

/*
Q.1625

You are given a string s of even length consisting of digits from 0 to 9, and two integers a and b.

You can apply either of the following two operations any number of times and in any order on s:

Add a to all odd indices of s (0-indexed). Digits post 9 are cycled back to 0. For example, if s = "3456" and a = 5, s becomes "3951".
Rotate s to the right by b positions. For example, if s = "3456" and b = 1, s becomes "6345".
Return the lexicographically smallest string you can obtain by applying the above operations any number of times on s.

A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b. For example, "0158" is lexicographically smaller than "0190" because the first position they differ is at the third letter, and '5' comes before '9'.
 */
public class LexicographicallySmallestStringAfterApplyingOperations {
    public String findLexSmallestString(String s, int a, int b) {
        if (s.length() < 2) return s;

        Set<String> values = new HashSet<>();
        calc(s, a, b, values);

        return values.stream().sorted().findFirst().get();
    }

    private void calc(String s, int a, int b, Set<String> values) {
        values.add(s);
        String add = add(s, a);
        String rotate = rotate(s, b);

        if (values.add(add)) calc(add, a, b, values);
        if (values.add(rotate)) calc(rotate, a, b, values);
    }

    private String add(String s, int a) {
        String[] splits = s.split("");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splits.length; i++) {
            int value = Integer.parseInt(splits[i]);

            if (i % 2 == 1) {
                value = (value + a) % 10;
            }

            sb.append(value);
        }

        return sb.toString();
    }

    private String rotate(String s, int b) {
        StringBuilder sb = new StringBuilder();
        int size = s.length();
        int index = b % size;

        while(sb.length() < size) {
            sb.append(s.charAt(index));
            index = (index + 1) % size;
        }

        return sb.toString();
    }
}

/*
bfs
public String findLexSmallestString(String s, int a, int b) {
    int n = s.length();
    String smallest = s;
    Queue<String> q = new LinkedList<>();
    q.offer(s);
    Set<String> seen = new HashSet<>(q);
    while (!q.isEmpty()) {
        String cur = q.poll();
        if (smallest.compareTo(cur) > 0)
            smallest = cur;
        char[] ca = cur.toCharArray();
        for (int i = 1; i < ca.length; i += 2) // add operation.
            ca[i] = (char)((ca[i] - '0' + a) % 10 + '0');
        String addA = String.valueOf(ca);
        if (seen.add(addA))
            q.offer(addA);
        String rotate = cur.substring(n - b) + cur.substring(0, n - b); // rotation.
        if (seen.add(rotate))
            q.offer(rotate);
    }
    return smallest;
}

dfs
private String smallest;
public String findLexSmallestString(String s, int a, int b) {
    smallest = s;
    dfs(s, a, b, new HashSet<>());
    return smallest;
}
private void dfs(String s, int a, int b, Set<String> seen) {
    if (seen.add(s)) {
        if (smallest.compareTo(s) > 0) {
            smallest = s;
        }
        char[] ca = s.toCharArray();
        for (int i = 1; i < ca.length; i += 2) {
            ca[i] = (char)((ca[i] - '0' + a) % 10 + '0');
        }
        dfs(String.valueOf(ca), a, b, seen);
        dfs(s.substring(b) + s.substring(0, b), a, b, seen);
    }
}
 */
