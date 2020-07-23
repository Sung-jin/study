import collection.CompareCollectionPerformance;
import stream.ParallelExample;

import java.util.ArrayList;
import java.util.LinkedList;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {
//        collectionCodes();
//        streamCodes();
        launch(args);
    }

    private static void collectionCodes() {
        CompareCollectionPerformance compareCollectionPerformance = new CompareCollectionPerformance();

        ArrayList list1 = new ArrayList<String>();
        LinkedList list2 = new LinkedList<String>();

        System.out.println("ArrayList");
        compareCollectionPerformance.compareList(list1);

        System.out.println("LinkedList");
        compareCollectionPerformance.compareList(list2);
    }

    private static void streamCodes() {
        ParallelExample parallelExample = new ParallelExample();

        parallelExample.parallelTest();
    }
}
