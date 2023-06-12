package highScoreKit.stackAndQueue;

import java.util.*;
import java.util.stream.Collectors;

/*
프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.

또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 return 하도록 solution 함수를 완성하세요.

제한 사항
작업의 개수(progresses, speeds배열의 길이)는 100개 이하입니다.
작업 진도는 100 미만의 자연수입니다.
작업 속도는 100 이하의 자연수입니다.
배포는 하루에 한 번만 할 수 있으며, 하루의 끝에 이루어진다고 가정합니다. 예를 들어 진도율이 95%인 작업의 개발 속도가 하루에 4%라면 배포는 2일 뒤에 이루어집니다.
 */
public class FeatureDevelopment {
    private final static int DONE_PROCESS_RATIO = 100;

    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> deployCounts = new ArrayList<>();
        Queue<Integer> progressRatio = Arrays.stream(progresses)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        int[] raminJobSpeed =  Arrays.copyOf(speeds, speeds.length);

        while(!progressRatio.isEmpty()) {
            processJob(progressRatio, raminJobSpeed);

            if (checkDoneFirstJob(progressRatio)) {
                int doneJobCount = 0;
                while (checkDoneFirstJob(progressRatio)) {
                    doneJobCount += 1;
                    progressRatio.poll();
                }

                raminJobSpeed = Arrays.copyOfRange(raminJobSpeed, doneJobCount, raminJobSpeed.length);
                deployCounts.add(doneJobCount);
            }
        }

        return deployCounts.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private void processJob(Queue<Integer> progresses, int[] speeds) {
        for (int speed : speeds) {
            Integer ratio = progresses.poll();

            if (ratio == null) throw new RuntimeException("진행률 정보가 없습니다.");

            progresses.offer(ratio + speed);
        }
    }

    private boolean checkDoneFirstJob(Queue<Integer> progresses) {
        return progresses.peek() != null && progresses.peek() >= DONE_PROCESS_RATIO;
    }
}

/*
// 자료구조/알고리즘: 큐
// 시간 복잡도: O(n)
// 공간 복잡도: O(n)

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> deployPlan = new ArrayList<>();
        Queue<Integer> finishedDays = new LinkedList<>();

        for (int i = 0; i < progresses.length; i++) {
            int finishedDay = canFinishWithin(progresses[i], speeds[i]);
            if (!finishedDays.isEmpty() && finishedDays.peek() < finishedDay) {
                deployPlan.add(finishedDays.size());
                finishedDays.clear();
            }
            finishedDays.offer(finishedDay);
        }
        deployPlan.add(finishedDays.size());

        return deployPlan.stream().mapToInt(Integer::intValue).toArray();
    }

    private int canFinishWithin(int progress, int speed) {
        return (int) Math.ceil((double)(100.0 - progress) / speed);
    }
}
 */
