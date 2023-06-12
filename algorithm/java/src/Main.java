import highScoreKit.hash.HashExecute;
import highScoreKit.stackAndQueue.StackQueueExecute;

public class Main {
    public static void main(String[] args) {
        HashExecute he = new HashExecute();
        StackQueueExecute sqe = new StackQueueExecute();

        System.out.println("Hash");
        he.execute();
        System.out.println("------");
        System.out.println("Stack And Queue");
        sqe.execute();
    }
}
