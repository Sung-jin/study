package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class IOUtils {
    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static Map<String, ArrayList<String>> readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        Map<String, ArrayList<String>> convertBody = new HashMap<>();

        Arrays.stream(String
                .copyValueOf(body)
                .split("&")
        ).forEach(value -> {
            String[] splitBody = value.split("=");
            if (convertBody.containsKey(splitBody[0])) {
                convertBody.get(splitBody[0]).add(splitBody[1]);
            } else {
                convertBody.put(splitBody[0], new ArrayList<>(Collections.singletonList(splitBody[1])));
            }
        });

        return convertBody;
    }
}
