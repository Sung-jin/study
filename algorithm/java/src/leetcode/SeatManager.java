package leetcode;

/*
Q.1845. Seat Reservation Manager
Design a system that manages the reservation state of n seats that are numbered from 1 to n.

Implement the SeatManager class:

SeatManager(int n) Initializes a SeatManager object that will manage n seats numbered from 1 to n. All seats are initially available.
int reserve() Fetches the smallest-numbered unreserved seat, reserves it, and returns its number.
void unreserve(int seatNumber) Unreserves the seat with the given seatNumber.
 */
public class SeatManager {
    private boolean[] seat;
    private int lowestIndex;

    public SeatManager(int n) {
        seat = new boolean[n];
        lowestIndex = 0;
    }

    public int reserve() {
        int reservedIndex = lowestIndex;
        seat[lowestIndex] = true;

        for (int i = lowestIndex + 1; i < seat.length; i++) {
            if (!seat[i]) {
                lowestIndex = i;
                break;
            }
        }

        return reservedIndex + 1;
    }

    public void unreserve(int seatNumber) {
        int index = seatNumber - 1;

        seat[index] = false;

        if (index < lowestIndex) lowestIndex = index;
    }
}

/*
class SeatManager {
    PriorityQueue<Integer> pq;
    int count;
    public SeatManager(int n) {
        count = 1;
        pq = new PriorityQueue();
    }

    public int reserve() {
        if(pq.size()==0)
            return count++;

        return pq.poll();
    }

    public void unreserve(int seatNumber) {
        pq.add(seatNumber);
    }
}
 */