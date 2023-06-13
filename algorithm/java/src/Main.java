import highScoreKit.hash.HashExecute;
import highScoreKit.heap.HeapExecute;
import highScoreKit.stackAndQueue.StackQueueExecute;

public class Main {
    public static void main(String[] args) {
        HashExecute hashExecute = new HashExecute();
        StackQueueExecute stackQueueExecute = new StackQueueExecute();
        HeapExecute heapExecute = new HeapExecute();

        System.out.println("Hash");
        hashExecute.execute();
        System.out.println("------");
        System.out.println("Stack And Queue");
        stackQueueExecute.execute();
        System.out.println("------");
        System.out.println("Heap");
        heapExecute.execute();
    }
}
