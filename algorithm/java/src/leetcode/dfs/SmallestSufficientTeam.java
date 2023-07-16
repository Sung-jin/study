package leetcode.dfs;

import java.util.*;

/*
Q.1125

In a project, you have a list of required skills req_skills, and a list of people. The ith person people[i] contains a list of skills that the person has.

Consider a sufficient team: a set of people such that for every required skill in req_skills, there is at least one person in the team who has that skill. We can represent these teams by the index of each person.

For example, team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].
Return any sufficient team of the smallest possible size, represented by the index of each person. You may return the answer in any order.

It is guaranteed an answer exists.
 */
public class SmallestSufficientTeam {
    public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
        return matching(getContainSkillMap(req_skills), people, new ArrayList<>(), 0, req_skills.length)
                .stream().mapToInt(v -> v)
                .toArray();
    }

    private List<Integer> matching(Map<String, Boolean> reqSkillMap, List<List<String>> people, List<Integer> peoples, int index, int remainCount) {
        if (remainCount == 0) return peoples;

        List<Integer> res = new ArrayList<>();
        int minSize = Integer.MAX_VALUE;

        for (int i = index; i < people.size(); i++) {
            Map<String, Boolean> dupMap = new HashMap<>(reqSkillMap);
            List<Integer> dupList = new ArrayList<>(peoples);
            int cnt = remainCount;
            boolean isAddedPeople = false;
            for (String skill : people.get(i)) {
                boolean isAddedSkill = dupMap.get(skill);

                if (!isAddedSkill) {
                    isAddedPeople = true;
                    cnt--;
                    dupMap.put(skill, true);
                }
            }

            if (isAddedPeople) {
                dupList.add(i);
                List<Integer> afterMatching = matching(dupMap, people, dupList, i + 1, cnt);

                if (minSize > afterMatching.size()) {
                    minSize = afterMatching.size();
                    res = afterMatching;
                }
            }

            List<Integer> afterMatching = matching(reqSkillMap, people, peoples, i + 1, remainCount);

            if (minSize > afterMatching.size()) {
                minSize = afterMatching.size();
                res = afterMatching;
            }
        }

        return res;
    }

    private Map<String, Boolean> getContainSkillMap(String[] reqSkills) {
        Map<String, Boolean> map = new HashMap<>();
        for (String reqSkill : reqSkills) {
            map.put(reqSkill, false);
        }

        return map;
    }

    public List<List<String>> createPeople(String[][] people) {
        List<List<String>> res = new ArrayList<>();

        for (String[] person : people) {
            res.add(new ArrayList<>(Arrays.asList(person)));
        }

        return res;
    }
}

/*
public int[] smallestSufficientTeam(String[] req_skills, List<List<String>> people) {
    int n = req_skills.length, m = people.size();
    HashMap<String, Integer> skill_index = new HashMap<>();
    for (int i = 0; i < n; ++i)
        skill_index.put(req_skills[i], i);
    List<Integer>[] dp = new List[1 << n];
    dp[0] = new ArrayList<>();
    for (int i = 0; i < m; ++i) {
        int cur_skill = 0;
        for (String s : people.get(i))
            cur_skill |= 1 << skill_index.get(s);
        for (int prev = 0; prev < dp.length; ++prev) {
            if (dp[prev] == null) continue;
            int comb = prev | cur_skill;
            if (dp[comb] == null || dp[prev].size() + 1 < dp[comb].size()) {
                dp[comb] = new ArrayList<>(dp[prev]);
                dp[comb].add(i);
            }
        }
    }
    return dp[(1 << n) - 1].stream().mapToInt(i -> i).toArray();
}
 */