package highScoreKit.mockExam;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Third {
    public int one(int[][] reply) {
        int minParticipantCount = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[reply.length];

        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) {
                minParticipantCount++;
                visited[i] = true;
                queue.offer(i);

                while(!queue.isEmpty()) {
                    int queueSize = queue.size();

                    for (int j = 0; j < queueSize; j++) {
                        int participantIndex = queue.poll();

                        for (int k = i + 1; k < reply.length; k++) {
                            int[] wantedParticipants = reply[k];

                            if (!visited[k] && Arrays.stream(wantedParticipants).anyMatch(v -> v == participantIndex)) {
                                visited[k] = true;
                                queue.offer(k);
                            }
                        }
                    }
                }
            }
        }

        return minParticipantCount;
    }
    // 완전 탐색으로 변경해야 할듯?
}
