import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList list1 = new ArrayList<String>();
        LinkedList list2 = new LinkedList<String>();

        System.out.println("ArrayList");
        compareList(list1);

        System.out.println("LinkedList");
        compareList(list2);
    }

    private static void compareList(List list){
        long startTime;
        long endTime;

        System.out.println("==================================================");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.add(randomGenerateString());
        }
        endTime = System.nanoTime();
        System.out.println("순차 추가 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.get(i);
        }
        endTime = System.nanoTime();
        System.out.println("순차 검색 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.remove(0);
        }
        endTime = System.nanoTime();
        System.out.println("순차 삭제 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.add(new Random().nextInt(i + 1), randomGenerateString());
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 추가 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.get(new Random().nextInt(i + 1));
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 검색 : " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.remove(new Random().nextInt(list.size()));
        }
        endTime = System.nanoTime();
        System.out.println("랜덤 삭제 : " + (endTime - startTime) + " ns");
    }

    private static String randomGenerateString() {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        return new Random().ints(leftLimit, rightLimit + 1)
                .limit(1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
