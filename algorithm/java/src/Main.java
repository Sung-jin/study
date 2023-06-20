import highScoreKit.binarySearch.BinarySearchExecution;
import highScoreKit.fullExploration.FullExplorationExecution;
import highScoreKit.grahp.GraphExecution;
import highScoreKit.hash.HashExecute;
import highScoreKit.heap.HeapExecute;
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
    }
}
