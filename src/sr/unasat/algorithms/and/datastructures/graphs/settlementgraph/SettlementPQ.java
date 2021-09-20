package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

public class SettlementPQ {

    private final int SIZE = 20;
    private SettlementEdge[] queArray;
    private int size;

    public SettlementPQ() {
        queArray = new SettlementEdge[SIZE];
        size = 0;
    }

    public void insert(SettlementEdge item) {
        int j;
        for(j=0; j<size; j++)
            if( item.distance >= queArray[j].distance )
                break;
        for(int k=size-1; k>=j; k--)
            queArray[k+1] = queArray[k];
        queArray[j] = item;
        size++;
    }

    public SettlementEdge removeMin() {
        return queArray[--size];
    }

    public void removeN(int n) {
        for(int j=n; j<size-1; j++)
            queArray[j] = queArray[j+1];
        size--;
    }

    public SettlementEdge peekMin() {
        return queArray[size-1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size==0);
    }

    public SettlementEdge peekN(int n) {
        return queArray[n];
    }

    public int find(int findDex) {
        for(int j=0; j<size; j++)
            if(queArray[j].destVert == findDex)
                return j;
        return -1;
    }
}
