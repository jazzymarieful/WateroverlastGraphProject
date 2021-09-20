package sr.unasat.algorithms.and.datastructures.tools;

import java.util.Arrays;

public class VertexEdgeQueue {

    private final int SIZE = 30;
    private VertexEdgeObject[] queArray;
    private int front;
    private int rear;

    public VertexEdgeQueue() {
        queArray = new VertexEdgeObject[SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(VertexEdgeObject item) {
        if(rear == SIZE-1)
            rear = -1;
        queArray[++rear] = item;
    }

    public VertexEdgeObject remove() {
        VertexEdgeObject temp = queArray[front++];
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
