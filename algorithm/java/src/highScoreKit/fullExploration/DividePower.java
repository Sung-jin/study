package highScoreKit.fullExploration;

import java.util.*;

/*
n개의 송전탑이 전선을 통해 하나의 트리 형태로 연결되어 있습니다. 당신은 이 전선들 중 하나를 끊어서 현재의 전력망 네트워크를 2개로 분할하려고 합니다. 이때, 두 전력망이 갖게 되는 송전탑의 개수를 최대한 비슷하게 맞추고자 합니다.

송전탑의 개수 n, 그리고 전선 정보 wires가 매개변수로 주어집니다. 전선들 중 하나를 끊어서 송전탑 개수가 가능한 비슷하도록 두 전력망으로 나누었을 때, 두 전력망이 가지고 있는 송전탑 개수의 차이(절대값)를 return 하도록 solution 함수를 완성해주세요.

제한사항
n은 2 이상 100 이하인 자연수입니다.
wires는 길이가 n-1인 정수형 2차원 배열입니다.
wires의 각 원소는 [v1, v2] 2개의 자연수로 이루어져 있으며, 이는 전력망의 v1번 송전탑과 v2번 송전탑이 전선으로 연결되어 있다는 것을 의미합니다.
1 ≤ v1 < v2 ≤ n 입니다.
전력망 네트워크가 하나의 트리 형태가 아닌 경우는 입력으로 주어지지 않습니다.
 */
public class DividePower {
    public int solution(int n, int[][] wires) {
        int answer = n;

        Arrays.sort(wires, Comparator.comparingInt(a -> a[0]));

        for (int i = 0; i < wires.length; i++) {
            answer = Math.min(answer, getDiffWhenDivided(wires, i));
        }

        return answer;
    }

    int getDiffWhenDivided(int[][] wires, int index) {
        int[][] left = Arrays.copyOfRange(wires, 0, index);
        int[][] right = Arrays.copyOfRange(wires, index + 1, wires.length);
        Set<Integer> connected1 = new HashSet<>();
        Set<Integer> connected2 = new HashSet<>();

        connected1.add(wires[index][0]);
        connected2.add(wires[index][1]);

        for (int[] value : left) {
            int from = value[0];
            int to = value[1];

            if (connected1.contains(from) || connected1.contains(to)) {
                connected1.add(from);
                connected1.add(to);
            } else {
                connected2.add(from);
                connected2.add(to);
            }
        }
        for (int[] value : right) {
            int from = value[0];
            int to = value[1];

            if (connected1.contains(from) || connected1.contains(to)) {
                connected1.add(from);
                connected1.add(to);
            } else {
                connected2.add(from);
                connected2.add(to);
            }
        }

        return Math.abs(left.length - right.length);
    }
}

/*
// 자료구조/알고리즘: 완전탐색 (DFS)
// 시간 복잡도: O(n + m)
// 공간 복잡도: O(n^2)

class Solution {
    int n;
    int minDiff;

    public int solution(int n, int[][] wires) {
        this.n = n;
        this.minDiff = n;

        boolean[] visited = new boolean[n + 1]; // O(n) 공간
        boolean[][] connected = new boolean[n + 1][n + 1]; // O(n^2) 공간
        for (int[] wire : wires) { // O(m) 시간, m = wires.length
            connected[wire[0]][wire[1]] = true;
            connected[wire[1]][wire[0]] = true;
        }

        countConnectedNodes(connected, visited, 1); // O(n) 시간

        return minDiff;
    }

    private int countConnectedNodes(boolean[][] connected, boolean[] visited, int node){
        visited[node] = true;
        int connectedNodes = 1;
        for (int i = 1; i <= n; i++) {
            if (!visited[i] && connected[node][i]) {
                visited[i] = true;
                connectedNodes += countConnectedNodes(connected, visited, i);
            }
        }
        int diff = Math.abs(connectedNodes - (n - connectedNodes));
        minDiff = Math.min(minDiff, diff);
        return connectedNodes;
    }
}
 */