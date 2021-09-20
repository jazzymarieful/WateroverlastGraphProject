package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

public interface Stack {

    public void push(int item);

    public int pop();

    public int peek();

    public boolean isEmpty();
}
