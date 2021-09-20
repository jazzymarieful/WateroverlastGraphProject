package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

public class PathStack {

    private final int SIZE = 100;
    private Settlement[] stack;
    private int top;

    public PathStack() {
        stack = new Settlement[SIZE];
        top = -1;
    }

    public void push(Settlement item) {
        stack[++top] = item;
    }

    public Settlement pop() {
        return stack[top--];
    }

    public Settlement peek() {
        return stack[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

}
