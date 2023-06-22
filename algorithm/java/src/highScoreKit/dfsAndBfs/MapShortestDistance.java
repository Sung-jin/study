package highScoreKit.dfsAndBfs;

import java.util.LinkedList;

/*
ROR 게임은 두 팀으로 나누어서 진행하며, 상대 팀 진영을 먼저 파괴하면 이기는 게임입니다. 따라서, 각 팀은 상대 팀 진영에 최대한 빨리 도착하는 것이 유리합니다.

지금부터 당신은 한 팀의 팀원이 되어 게임을 진행하려고 합니다.

만약, 상대 팀이 자신의 팀 진영 주위에 벽을 세워두었다면 상대 팀 진영에 도착하지 못할 수도 있습니다.

게임 맵의 상태 maps가 매개변수로 주어질 때, 캐릭터가 상대 팀 진영에 도착하기 위해서 지나가야 하는 칸의 개수의 최솟값을 return 하도록 solution 함수를 완성해주세요. 단, 상대 팀 진영에 도착할 수 없을 때는 -1을 return 해주세요.

제한사항
maps는 n x m 크기의 게임 맵의 상태가 들어있는 2차원 배열로, n과 m은 각각 1 이상 100 이하의 자연수입니다.
n과 m은 서로 같을 수도, 다를 수도 있지만, n과 m이 모두 1인 경우는 입력으로 주어지지 않습니다.
maps는 0과 1로만 이루어져 있으며, 0은 벽이 있는 자리, 1은 벽이 없는 자리를 나타냅니다.
처음에 캐릭터는 게임 맵의 좌측 상단인 (1, 1) 위치에 있으며, 상대방 진영은 게임 맵의 우측 하단인 (n, m) 위치에 있습니다.
 */
public class MapShortestDistance {
    public int solution(int[][] maps) {
        Graph graph = new Graph(maps);

        return graph.getDistnace(0, maps.length * maps[0].length - 1);
    }

    class Graph {
        Node[] nodes;
        int minDistance = Integer.MAX_VALUE;

        Graph(int[][] maps) {
            this.nodes = new Node[maps.length * maps[0].length];

            for (int y = 0; y < maps.length; y++) {
                for (int x = 0; x < maps[y].length; x++) {
                    if (maps[y][x] == 1) {
                        int index = y * 5 + x;
                        nodes[index] = new Node(index);

                        Node top = y - 1 >= 0 ? nodes[index - 5] : null;
                        Node left = x -1 >= 0 ? nodes[index - 1] : null;

                        if (top != null) {
                            nodes[index].add(top);
                            top.add(nodes[index]);
                        }
                        if (left != null) {
                            nodes[index].add(left);
                            left.add(nodes[index]);
                        }
                    }
                }
            }
        }

        int getDistnace(int from, int to) {
            this.minDistance = Integer.MAX_VALUE;
            Node start = nodes[from];
            if (start == null) return -1;

            start.visitied = true;
            dfs(start, to, 0);

            for (Node node : nodes) {
                if (node != null) node.visitied = false;
            }

            if (this.minDistance == Integer.MAX_VALUE) return -1;
            else return this.minDistance + 1;
        }

        void dfs(Node node, int target, int distance) {
            for (Node next : node.adjacency) {
                if (!next.visitied) {
                    next.visitied = true;

                    if (target == next.value) this.minDistance = Math.min(this.minDistance, distance + 1);
                    else dfs(next, target, distance + 1);

                    next.visitied = false;
                }
            }
        }
    }

    class Node {
        int value;
        boolean visitied = false;
        LinkedList<Node> adjacency = new LinkedList<>();

        Node(int value) {
            this.value = value;
        }

        void add(Node node) {
            this.adjacency.add(node);
        }
    }
}

/*
// 자료구조/알고리즘: BFS
// 시간 복잡도: O(n * m)
// 공간 복잡도: O(n * m)

class Solution {
    final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // right, left, down, up

    public int solution(int[][] maps) {
        int rowSize = maps.length, colSize = maps[0].length;
        boolean[][] visited = new boolean[rowSize][colSize]; // O(n * m) 공간

        Queue<int[]> nextCells = new LinkedList<>(); // O(n * m) 공간
        nextCells.offer(new int[] {0, 0});
        visited[0][0] = true;

        int distance = 0;
        while (!nextCells.isEmpty()) { // O(n * m) 시간
            int size = nextCells.size();
            distance++;

            for (int i = 0; i < size; i++) {
                int[] cell = nextCells.poll();
                if (cell[0] == rowSize - 1 && cell[1] == colSize - 1) {
                    return distance;
                }
                for (int[] direction : DIRECTIONS) {
                    int nextX = cell[0] + direction[0];
                    int nextY = cell[1] + direction[1];
                    boolean isInRange =
                        nextX >= 0 && nextX < rowSize &&
                        nextY >= 0 && nextY < colSize;
                    if (isInRange && !visited[nextX][nextY] && maps[nextX][nextY] == 1) {
                        nextCells.offer(new int[] {nextX, nextY});
                        visited[nextX][nextY] = true;
                    }
                }
            }
        }
        return -1;
    }
}
 */