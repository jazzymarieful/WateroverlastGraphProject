package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

public class DistanceParent {
    public int parentVert;
    public int distance;

    public DistanceParent(int parentVertex, int distance) {
        this.parentVert = parentVertex;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "DistanceParent{" +
                "pv=" + parentVert +
                ", d=" + distance +
                '}';
    }
}
