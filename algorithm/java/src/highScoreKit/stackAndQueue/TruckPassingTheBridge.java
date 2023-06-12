package highScoreKit.stackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

/*
트럭 여러 대가 강을 가로지르는 일차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 다리에는 트럭이 최대 bridge_length대 올라갈 수 있으며, 다리는 weight 이하까지의 무게를 견딜 수 있습니다. 단, 다리에 완전히 오르지 않은 트럭의 무게는 무시합니다.

예를 들어, 트럭 2대가 올라갈 수 있고 무게를 10kg까지 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.

경과 시간	다리를 지난 트럭	다리를 건너는 트럭	대기 트럭
0	[]                  []              [7,4,5,6]
1~2	[]                  [7]             [4,5,6]
3	[7]	                [4]             [5,6]
4	[7]	                [4,5]	        [6]
5	[7,4]	            [5]	            [6]
6~7	[7,4,5]	            [6]	            []
8	[7,4,5,6]	        []	            []
따라서, 모든 트럭이 다리를 지나려면 최소 8초가 걸립니다.

solution 함수의 매개변수로 다리에 올라갈 수 있는 트럭 수 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭 별 무게 truck_weights가 주어집니다. 이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.

제한 조건
bridge_length는 1 이상 10,000 이하입니다.
weight는 1 이상 10,000 이하입니다.
truck_weights의 길이는 1 이상 10,000 이하입니다.
모든 트럭의 무게는 1 이상 weight 이하입니다.
 */
public class TruckPassingTheBridge {
    /*
    정답 제출시 일부 런타임 에러가 발생함. 왜인지 모르겠음.
     */
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int onBridgeTruckWeight = 0;
        int time = 0;
        Queue<PassingTruckInfo> onBridgeTruckWeights = new LinkedList<>();

        for (int truckWeight : truck_weights) {
            PassingTruckInfo truckInfo = new PassingTruckInfo(truckWeight, bridge_length);

            if (onBridgeTruckWeights.isEmpty()) {
                addTruck(onBridgeTruckWeights, truckInfo);
                onBridgeTruckWeight += truckWeight;
                time += 1;
            } else {
                while(onBridgeTruckWeight + truckWeight > weight && onBridgeTruckWeights.size() <= bridge_length) {
                    PassingTruckInfo firstTruck = moveTruck(onBridgeTruckWeights);

                    time += firstTruck.remainLength;
                    onBridgeTruckWeight -= firstTruck.weight;
                }

                addTruck(onBridgeTruckWeights, truckInfo);
                onBridgeTruckWeight += truckWeight;
                time += 1;
            }
        }

        PassingTruckInfo lastTruck = null;
        for (PassingTruckInfo truck : onBridgeTruckWeights) {
            lastTruck = truck;
        }

        if (lastTruck != null) {
            time += lastTruck.remainLength + 1;
        }

        return time;
    }

    private PassingTruckInfo moveTruck(Queue<PassingTruckInfo> info) {
        PassingTruckInfo firstTruck = info.poll();

        for (int i = 0; i < info.size(); i++) {
            PassingTruckInfo truck = info.poll();
            truck.remainLength -= firstTruck.remainLength;
            info.offer(truck);
        }

        return firstTruck;
    }

    private void addTruck(Queue<PassingTruckInfo> info, PassingTruckInfo addTruck) {
        info.offer(addTruck);
        int size = info.size();

        for (int i = 0; i < size; i++) {
            PassingTruckInfo truck = info.poll();
            truck.remainLength -= 1;

            if (truck.remainLength != -1) {
                info.offer(truck);
            }
        }
    }

    private class PassingTruckInfo {
        int weight;
        int remainLength;

        public PassingTruckInfo(int weight, int remainLength) {
            this.weight = weight;
            this.remainLength = remainLength;
        }
    }
}

/*
// 자료구조/알고리즘: 큐
// 시간 복잡도: O(n)
// 공간 복잡도: O(n)

class Solution {
    public int solution(int bridgeLength, int weight, int[] truckWeights) {
        Queue<Integer> waitList = new LinkedList<>();
        for (int truckWeight : truckWeights) {
            waitList.offer(truckWeight);
        }
        Queue<Integer> entryTimes = new LinkedList<>();
        Queue<Integer> trucksOnBridge = new LinkedList<>();
        int time = 0, totalWeight = 0;
        while (!waitList.isEmpty() || !entryTimes.isEmpty()) {
            time++;
            if (!entryTimes.isEmpty() && entryTimes.peek() + bridgeLength == time) {
                entryTimes.poll();
                totalWeight -= trucksOnBridge.poll();
            }
            if (!waitList.isEmpty() && waitList.peek() + totalWeight <= weight) {
                int currentTruck = waitList.poll();
                entryTimes.add(time);
                trucksOnBridge.offer(currentTruck);
                totalWeight += currentTruck;
            }
        }
        return time;
    }
}
 */
