package leetcode.slidingWindow;

import java.util.Stack;

/*
Q.735

We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.
 */
public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> remainAsteroids = new Stack<>();
        for (int asteroid : asteroids) {
            crashAsteroid(remainAsteroids, asteroid);
        }

        return remainAsteroids.stream().mapToInt(v -> v).toArray();
    }

    private void crashAsteroid(Stack<Integer> asteroids, int target) {
        if (asteroids.isEmpty()) {
            asteroids.add(target);
            return;
        }

        while (!asteroids.isEmpty()) {
            int nowSize = asteroids.peek();
            int absTarget = Math.abs(target);

            if (!(nowSize > 0 && target < 0)) {
                asteroids.add(target);
                return;
            }

            if (nowSize > absTarget) return;
            else if (nowSize < absTarget) asteroids.pop();
            else {
                asteroids.pop();
                return;
            }
        }

        asteroids.add(target);
    }
}

/*
 public int[] asteroidCollision(int[] a) {
    LinkedList<Integer> s = new LinkedList<>(); // use LinkedList to simulate stack so that we don't need to reverse at end.
    for (int i = 0; i < a.length; i++) {
        if (a[i] > 0 || s.isEmpty() || s.getLast() < 0)
            s.add(a[i]);
        else if (s.getLast() <= -a[i])
            if (s.pollLast() < -a[i]) i--;
    }
    return s.stream().mapToInt(i->i).toArray();
}
 */