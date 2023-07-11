package leetcode.dynamicProgramming;

/*
Q.1359
Given n orders, each order consist in pickup and delivery services.

Count all valid pickup/delivery possible sequences such that delivery(i) is always after of pickup(i).

Since the answer may be too large, return it modulo 10^9 + 7.
 */
public class CountAllValidPickupAndDeliveryOptions {
    public int countOrders(int n) {
        boolean[] pickupVisits = new boolean[n];
        boolean[] deliveryVisits = new boolean[n];

        return -1;
    }

    private int processing(boolean[] pickupVisits, boolean[] deliverVisits, int pickupIndex) {
        int possibleCount = 0;

        for (int i = 0; i < pickupVisits.length; i++) {
            pickupVisits[i] = true;

            for (int j = 0; j < deliverVisits.length; j++) {
            }
        }



        for (int i = 0; i < pickupVisits.length; i++) {
            if (!pickupVisits[i]) {
                pickupVisits[i] = true;
            }

            for (int j = 0; j < deliverVisits.length; j++) {
                if (!deliverVisits[j] && pickupVisits[j]) {

                }
            }
        }

        return -1;
    }
}

/*
Intuition 1
Assume we have already n - 1 pairs, now we need to insert the nth pair.
To insert the first element, there are n * 2 - 1 chioces of position。
To insert the second element, there are n * 2 chioces of position。
So there are (n * 2 - 1) * n * 2 permutations.
Considering that delivery(i) is always after of pickup(i), we need to divide 2.
So it's (n * 2 - 1) * n.


Intuition 2
We consider the first element in all 2n elements.
The first must be a pickup, and we have n pickups as chioce.
Its pair can be any position in the rest of n*2-1 positions.
So it's (n * 2 - 1) * n.


Intuition 3
The total number of all permutation obviously eauqls to 2n!.
For each pair, the order is determined, so we need to divide by 2.
So the final result is (2n)!/(2^n)

public int countOrders(int n) {
    long res = 1, mod = (long)1e9 + 7;
    for (int i = 1; i <= n; ++i)
        res = res * (i * 2 - 1) * i % mod;
    return (int)res;
}
 */

/*
class Solution {
    public int countOrders(int n) {
        List<String> pickup = new ArrayList<>();
        List<String> delivery = new ArrayList<>();
        for (int i=1; i<=n; i++) {
            String p = "P" + i;
            String d = "D" + i;
            pickup.add(p);
            delivery.add(d);
        }
        List<List<String>> res = new ArrayList<>();
        getCombos(pickup, delivery, res, new ArrayList<>(), new boolean[n], new boolean[n]);
        System.out.println(res);
        return res.size();
    }

    public void getCombos (List<String> pickup, List<String> delivery, List<List<String>> res, List<String> curr, boolean[] picked, boolean[] delivered) {
        if (curr.size() == pickup.size() * 2)
            res.add(new ArrayList<>(curr));

        for (int i=0; i<pickup.size(); i++) {
            if (!picked[i]) {
                curr.add(pickup.get(i));
                picked[i] = true;
                getCombos(pickup, delivery, res, curr, picked, delivered);
                curr.remove(curr.size()-1);
                picked[i] = false;
            }
        }
        for (int i=0; i<delivery.size(); i++) {
            if (picked[i] && !delivered[i]) {
                curr.add(delivery.get(i));
                delivered[i] = true;
                getCombos(pickup, delivery, res, curr, picked, delivered);
                curr.remove(curr.size()-1);
                delivered[i] = false;
            }
        }
    }
}
 */