package sr.unasat.algorithms.and.datastructures.tools;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

public interface Queue {

    public void insert(int item);

    public int remove();

    public boolean isEmpty();

    public void makeEmpty();

    public void showQueue();
}
