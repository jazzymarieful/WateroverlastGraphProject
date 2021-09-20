package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

import java.util.Arrays;

public class EndangeredSettlementQueue {

    private final int SIZE = 30;
    private Settlement[] queArray;
    private int front;
    private int rear;

    public EndangeredSettlementQueue() {
        queArray = new Settlement[SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(Settlement item) {
        if(rear == SIZE-1)
            rear = -1;
        queArray[++rear] = item;
    }

    public Settlement remove() {
        Settlement temp = queArray[front++];
        if (front == SIZE)
            front = 0;
        return temp;
    }

    public boolean isEmpty() {
        return ((rear + 1 == front) || (front + SIZE - 1 == rear));
    }

    public void makeEmpty() {
        front = 0;
        rear = -1;
    }

    public void showQueue() {
        System.out.println(Arrays.toString(queArray));
    }
}
