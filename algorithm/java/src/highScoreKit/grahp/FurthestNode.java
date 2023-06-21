package highScoreKit.grahp;

import java.util.*;

/*
n개의 노드가 있는 그래프가 있습니다. 각 노드는 1부터 n까지 번호가 적혀있습니다. 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다. 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.

노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때, 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.

제한사항
노드의 개수 n은 2 이상 20,000 이하입니다.
간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.

문제를 잘못 이해했네. 가장 먼 노드의 길이가 아닌, 1번 기준으로 가장 먼 노드에 해당하는 수였네. <- 이는 제네릭으로 할 필요 없이 무조건 1이 있고, 모두 연결되어 있다는 가정하에 풀었어야 했네.
 */
public class FurthestNode {
    public int solution(int n, int[][] edge) {
        Graph<Integer> graph = new Graph(n);

        for (int[] node : edge) {
            graph.add(node[0], node[1]);
        }

        return graph.getMaxDistance();
    }

    class Graph<T> {
        private final Set<T> elements = new HashSet<>();
        private final Map<T, LinkedList<T>> adjacency = new HashMap<>();
        private int minDistance = Integer.MAX_VALUE;
        private int maxDistance;

        public Graph(int size) {
            this.maxDistance = size - 1;
        }

        public void add(T from, T to) {
            this.elements.add(from);
            this.elements.add(to);

            addAdjacency(from, to);
            addAdjacency(to, from);
        }

        private void addAdjacency(T from, T to) {
            LinkedList<T> elementAdjacency = adjacency.getOrDefault(from, new LinkedList<>());
            if (!elementAdjacency.contains(to)) elementAdjacency.add(to);
            adjacency.put(from, elementAdjacency);
        }

        public int getMaxDistance() {
            int maxDistance = 0;
            this.minDistance = this.maxDistance;

            for (T from : this.elements) {
                for (T to : this.elements) {
                    calculateMinDistance(from, to, createVisited(from), 0);
                    maxDistance = Math.max(maxDistance, this.minDistance);
                    this.minDistance = this.maxDistance;
                }
            }

            return maxDistance;
        }

        private HashMap<T, Boolean> createVisited(T start) {
            HashMap<T, Boolean> visited = new HashMap<>();
            for (T element : this.elements) {
                if (element == start) visited.put(element, true);
                else  visited.put(element, false);
            }

            return visited;
        }

        private void calculateMinDistance(T from, T to, Map<T, Boolean> visited, int distance) {
            if (from == to) {
                this.minDistance = Math.min(minDistance, distance);
                return;
            }

            for (T target : adjacency.get(from)) {
                if (visited.get(target)) continue;

                if (target == to) {
                    this.minDistance = Math.min(minDistance, distance + 1);
                    return;
                } else {
                    visited.put(from, true);
                    calculateMinDistance(target, to, visited, distance + 1);
                    visited.put(from, false);
                }
            }
        }
    }
}

/*
BFS
- Queue - 자식/이웃 노드
- 반복문 while (!queue.isEmpty())
- Visted 사용
- 최단 거리, 최소 스텝

1            A

2        B       C

3     D

Level-order BFS
queue [A]
1: poll A -> queue [B, C]
queue size = 2
2: poll B -> queue [C, D]
2: poll C -> queue [D]
queue size = 1
3: poll D -> queue []

// 자료구조/알고리즘: BFS
// 시간 복잡도: O(n * m)
// 공간 복잡도: O(n)

class Solution {
    public int solution(int n, int[][] edges) {
        Node[] nodes = new Node[n + 1]; // O(n) 공간
        for (int i = 1; i <= n; i++) { // O(n) 시간
            nodes[i] = new Node(i);
        }

        for (int[] edge : edges) { // O(m) 시간, m = edges.length
            nodes[edge[0]].addNeighbor(nodes[edge[1]]);
            nodes[edge[1]].addNeighbor(nodes[edge[0]]);
        }

        boolean[] visited = new boolean[n + 1];
        Queue<Node> nodesToVisit = new LinkedList<>();

        nodesToVisit.offer(nodes[1]);
        visited[1] = true;

        int distance = 0;
        int[] nodesPerDistance = new int[n + 1];

        while (!nodesToVisit.isEmpty()) { // O(n * m) 시간
            int size = nodesToVisit.size();
            distance++;
            for (int i = 0; i < size; i++) {
                Node currentNode = nodesToVisit.poll();
                nodesPerDistance[distance]++;
                for (Node nextNode : currentNode.neighbors) {
                    if (!visited[nextNode.value]) {
                        nodesToVisit.offer(nextNode);
                        visited[nextNode.value] = true;
                    }
                }
            }
        }
        return nodesPerDistance[distance];
    }

    class Node {
        int value;
        List<Node> neighbors;

        public Node(int value) {
            this.value = value;
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Node node) {
            this.neighbors.add(node);
        }
    }
}
 */