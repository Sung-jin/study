package highScoreKit.hash;

import java.util.HashMap;

/**
 * 수많은 마라톤 선수들이 마라톤에 참여하였습니다. 단 한 명의 선수를 제외하고는 모든 선수가 마라톤을 완주하였습니다.
 *
 * 마라톤에 참여한 선수들의 이름이 담긴 배열 participant와 완주한 선수들의 이름이 담긴 배열 completion이 주어질 때, 완주하지 못한 선수의 이름을 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 마라톤 경기에 참여한 선수의 수는 1명 이상 100,000명 이하입니다.
 * completion의 길이는 participant의 길이보다 1 작습니다.
 * 참가자의 이름은 1개 이상 20개 이하의 알파벳 소문자로 이루어져 있습니다.
 * 참가자 중에는 동명이인이 있을 수 있습니다.
 */
public class UnreachedAthlete {
    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> participantMap = new HashMap<>();

        for (String p : participant) {
            if (participantMap.containsKey(p)) {
                participantMap.put(p, participantMap.get(p) + 1);
            } else {
                participantMap.put(p, 1);
            }
        }

        for (String c : completion) {
            if (participantMap.get(c) == 1) {
                participantMap.remove(c);
            } else {
                participantMap.put(c, participantMap.get(c) - 1);
            }
        }

        return participantMap.keySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("도착하지 못한 선수를 찾을 수 없습니다."));
    }
}

/*
// 자료구조/알고리즘: Map
// 시간 복잡도: O(n)
// 공간 복잡도: O(n)

class Solution {
    public String solution(String[] participants, String[] completion) {
        Map<String, Integer> completed = new HashMap<>();
        for (String participant : completion) {
            completed.put(participant, completed.getOrDefault(participant, 0) + 1);
        }
        for (String participant : participants) {
            if (!completed.containsKey(participant) || completed.get(participant) == 0) {
                return participant;
            }
            completed.put(participant, completed.get(participant) - 1);
        }
        return "";
    }
}
 */
