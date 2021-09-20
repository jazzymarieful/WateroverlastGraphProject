package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;
import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;

import java.util.Arrays;

public class ObjectQueue {

    private final int SIZE = 30;
    private Object[] queArray;
    private int front;
    private int rear;

    public ObjectQueue() {
        queArray = new Object[SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(Object item) {
        if(rear == SIZE-1)
            rear = -1;
        queArray[++rear] = item;
    }

    public Object remove() {
        Object temp = queArray[front++];
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

