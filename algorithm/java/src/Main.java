import highScoreKit.binarySearch.BinarySearchExecution;
import highScoreKit.dfsAndBfs.DfsAndBfsExecute;
import highScoreKit.fullExploration.FullExplorationExecution;
import highScoreKit.fullExploration.MockExam;
import highScoreKit.grahp.GraphExecution;
import highScoreKit.greedy.GreedyExecute;
import highScoreKit.greedy.GymSuit;
import highScoreKit.hash.HashExecute;
import highScoreKit.heap.HeapExecute;
import highScoreKit.mockExam.MockExamExecute;
import highScoreKit.mockExam.Third;
import highScoreKit.sorting.SortingExecute;
import highScoreKit.stackAndQueue.StackQueueExecute;

public class Main {
    public static void main(String[] args) {
        HashExecute hashExecute = new HashExecute();
        StackQueueExecute stackQueueExecute = new StackQueueExecute();
        HeapExecute heapExecute = new HeapExecute();
        SortingExecute sortingExecute = new SortingExecute();
        FullExplorationExecution fullExplorationExecution = new FullExplorationExecution();
        BinarySearchExecution binarySearchExecution = new BinarySearchExecution();
        GraphExecution graphExecution = new GraphExecution();
        DfsAndBfsExecute dfsAndBfsExecute = new DfsAndBfsExecute();
        GreedyExecute greedyExecute = new GreedyExecute();

        MockExamExecute mockExamExecute = new MockExamExecute();

        System.out.println("Hash");
        hashExecute.execute();
        System.out.println("------");
        System.out.println("Stack And Queue");
        stackQueueExecute.execute();
        System.out.println("------");
        System.out.println("Heap");
        heapExecute.execute();
        System.out.println("------");
        System.out.println("Sorting");
        sortingExecute.execute();
        System.out.println("------");
        System.out.println("Full Exploration");
        fullExplorationExecution.execute();
        System.out.println("------");
        System.out.println("Binary Search");
        binarySearchExecution.execute();
        System.out.println("------");
        System.out.println("Graph");
        graphExecution.execute();
        System.out.println("------");
        System.out.println("Dfs and Bfs");
        dfsAndBfsExecute.execute();
        System.out.println("------");
        System.out.println("greedy");
        greedyExecute.execute();


        System.out.println("------");
        System.out.println("mock exam");
        mockExamExecute.execute();
    }
}
