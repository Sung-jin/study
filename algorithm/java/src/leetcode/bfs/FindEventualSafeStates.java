package leetcode.bfs;

import java.util.*;
import java.util.stream.Collectors;

/*
Q.802
There is a directed graph of n nodes with each node labeled from 0 to n - 1. The graph is represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, meaning there is an edge from node i to each node in graph[i].

A node is a terminal node if there are no outgoing edges. A node is a safe node if every possible path starting from that node leads to a terminal node (or another safe node).

Return an array containing all the safe nodes of the graph. The answer should be sorted in ascending order.
 */
public class FindEventualSafeStates {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> answer = new ArrayList<>();
        int totalEdgeCount = 0;

        for (int[] edges : graph) {
            totalEdgeCount += edges.length;
        }

        for (int i = 0; i < graph.length; i++) {
            if (!checkCircling(graph, i, totalEdgeCount)) answer.add(i);
        }

        return answer.stream().sorted().collect(Collectors.toList());
    }

    private boolean checkCircling(int[][] graph, int startNode, int totalEdgeCount) {
        Queue<Integer> queue = new LinkedList<>();
        int moveCount = 0;
        for (int i : graph[startNode]) {
            queue.offer(i);
        }

        while(!queue.isEmpty()) {
            int queueSize = queue.size();

            for (int i = 0; i < queueSize; i++) {
                int[] edges = graph[queue.poll()];

                for (int edge : edges) {
                    if (moveCount++ > totalEdgeCount * graph.length) return true;

                    queue.offer(edge);
                }
            }
        }

        return false;
    }
    // -> 서클링을 찾을 필요가 없네. 다음 노드를 일단 unsafe 로 만들고, 전체 흐름 중 unsafe 를 다시 탈 경우, 탔던 모든 경로는 unsafe 를 그대로 두고,
    // 모두 돌았는데 unsafe 를 다시 안타면, 탔던 모든 곳 (recursive 호출한 이전 대상들) 을 모두 safe 로 변경하고,
    // 모든 노드의 경우 safe 를 타는 경로가 있을 경우, 해당 노드는 safe 가 됨이 보장되네
}

/*
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        if(graph == null || graph.length == 0)  return res;

        int nodeCount = graph.length;
        int[] color = new int[nodeCount];

        for(int i = 0;i < nodeCount;i++){
            if(dfs(graph, i, color))    res.add(i);
        }

        return res;
    }
    public boolean dfs(int[][] graph, int start, int[] color){
        // 0:have not been visited
        // 1:safe
        // 2:unsafe
        if(color[start] != 0)   return color[start] == 1;

        color[start] = 2;
        for(int newNode : graph[start]){
            if(!dfs(graph, newNode, color))    return false;
        }
        color[start] = 1;

        return true;
    }
}
 */