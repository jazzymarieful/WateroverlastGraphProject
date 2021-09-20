package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

public class GraphStack implements Stack {

    private final int SIZE = 100;
    private int[] stack;
    private int top;

    public GraphStack() {
        stack = new int[SIZE];
        top = -1;
    }

    @Override
    public void push(int item) {
        stack[++top] = item;
    }

    @Override
    public int pop() {
        return stack[top--];
    }

    @Override
    public int peek() {
        return stack[top];
    }

    @Override
    public boolean isEmpty() {
        return (top == -1);
    }

}
