package highScoreKit.dfsAndBfs;

import java.util.Arrays;

/*
주어진 항공권을 모두 이용하여 여행경로를 짜려고 합니다. 항상 "ICN" 공항에서 출발합니다.

항공권 정보가 담긴 2차원 배열 tickets가 매개변수로 주어질 때, 방문하는 공항 경로를 배열에 담아 return 하도록 solution 함수를 작성해주세요.

제한사항
모든 공항은 알파벳 대문자 3글자로 이루어집니다.
주어진 공항 수는 3개 이상 10,000개 이하입니다.
tickets의 각 행 [a, b]는 a 공항에서 b 공항으로 가는 항공권이 있다는 의미입니다.
주어진 항공권은 모두 사용해야 합니다.
만일 가능한 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return 합니다.
모든 도시를 방문할 수 없는 경우는 주어지지 않습니다.
 */
public class TravelRoute {
    private static final String START_AIRLINE = "ICN";

    public String[] solution(String[][] tickets) {
        Arrays.sort(tickets, this::compareAirline);
        boolean[] visited = new boolean[tickets.length];
        String[] routes = new String[tickets.length + 1];
        routes[0] = START_AIRLINE;
        getRoutes(tickets, visited, START_AIRLINE, routes, 1);

        return routes;
    }

    private void getRoutes(String[][] tickets, boolean[] visited, String airline, String[] routes, int routeIndex) {
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i][0].equals(airline) && !visited[i]) {
                visited[i] = true;
                routes[routeIndex] = tickets[i][1];

                getRoutes(tickets, visited, tickets[i][1], routes, routeIndex + 1);
            }
        }
    }

    private int compareAirline(String[] a, String[] b) {
        int compareFrom = a[0].compareTo(b[0]);

        if (compareFrom == 0) {
            return a[1].compareTo(b[1]);
        } else return compareFrom;
    }
}

/*
// 자료구조/알고리즘: DFS
// 시간 복잡도: O(nm + mlogm)
// 공간 복잡도: O(n + m)

class Solution {
    private String[][] tickets;
    private boolean[] visited;
    private List<String> possibleRoutes;

    public String[] solution(String[][] tickets) {
        this.tickets = tickets;
        this.visited = new boolean[tickets.length]; // O(n) 공간, n = tickets.length
        this.possibleRoutes = new ArrayList<>(); // O(m) 공간, m = possibleRoutes.length

        addRoute("ICN", "ICN", 0); // O(n * m) 시간
        Collections.sort(possibleRoutes); // O(mlogm) 시간
        String bestRoute = possibleRoutes.get(0);

        return bestRoute.split(" ");
    }

    private void addRoute(String route, String departure, int currentLength) {
        // System.out.println("route = " + route); // 주석 해제 하시면 route가 추가되는 과정을 보실 수 있어요
        if (currentLength == tickets.length) {
            possibleRoutes.add(route);
        } else {
            for (int i = 0; i < tickets.length; i++) {
                if (!visited[i] && tickets[i][0].equals(departure)) {
                    String arrival = tickets[i][1];
                    visited[i] = true;
                    addRoute(route + " " + arrival, arrival, currentLength + 1);
                    visited[i] = false;
                }
            }
        }
    }
}
 */