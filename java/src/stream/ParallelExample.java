package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ParallelExample {
    public void parallelTest() {
        List<String> list = Arrays.asList("foo", "bar", "dfs", "baz", "qux");

        Stream<String> sequentialStream = list.stream();        // 순차 처리 스트림
        Stream<String> parallelStream = list.parallelStream();  // 병렬 처리 스트림

        sequentialStream.forEach(ParallelExample::print);
        parallelStream.forEach(ParallelExample::print);
    }

    private static void print(String str) {
        System.out.println(str + " : " + Thread.currentThread().getName());
    }
}
