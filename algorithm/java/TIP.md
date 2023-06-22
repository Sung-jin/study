### BFS

* Queue 를 통해서 자식/이웃 노드를 모두 넣는 형태
* 반복문 while (!queue.isEmpty())
* Visited 사용
* **최단 거리, 최소 스텝**

```java
class Graph {
    Node[] nodes;

    Graph(int size) {
        this.nodes = new Node[size];
        // init code
    }

    void bfs() {
        Node start = nodes[0];
        start.visited = true;
        Queue<Node> queue = new LinkedList<>();
        int distance = 0;
        
        while(!queue.isEmpty()) {
            distance++;
            int thisLevelSize = queue.size();
            
            for (int i = 0; i < thisLevelSize; i++) {
                Node node = queue.poll();
                System.out.println("distance " + thisLevelSize + ", value: " + node.value);

                for (Node next: node.adjacency) {
                    if (!next.visited) {
                        queue.offer(next);
                        next.visited = true;
                    }
                }
            }
        }
        
        for (Node value: this.nodes) {
            value.visited = false;
        }
    }
}

class Node {
    int value;
    boolean visited = false;
    LinkedList<Node> adjacency = new LinkedList<>();
}
```