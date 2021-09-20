package sr.unasat.algorithms.and.datastructures.tools;

import java.util.Arrays;

public class OneEdgeQueue implements Queue {

    private final int SIZE = 30;
    private int[] queArray;
    private int front;
    private int rear;

    public OneEdgeQueue() {
        queArray = new int[SIZE];
        front = 0;
        rear = -1;
    }

    @Override
    public void insert(int item) {
        if(rear == SIZE-1)
            rear = -1;
        queArray[++rear] = item;
    }

    @Override
    public int remove() {
        int temp = queArray[front++];
        if (front == SIZE)
            front = 0;
        return temp;
    }

    @Override
    public boolean isEmpty() {
        return ((rear + 1 == front) || (front + SIZE - 1 == rear));
    }

    @Override
    public void makeEmpty() {
        front = 0;
        rear = -1;
    }

    @Override
    public void showQueue() {
        System.out.println(Arrays.toString(queArray));
    }

}
