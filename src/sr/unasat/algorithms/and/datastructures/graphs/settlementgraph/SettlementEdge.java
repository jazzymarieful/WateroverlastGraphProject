package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

public class SettlementEdge {

    public int srcVert;
    public int destVert;
    public int distance;
    public final int originalDist; // zou final kunnen zijn, word nooit meer aangepast na eerste keer
    public boolean isEdgeAdjusted;

    public SettlementEdge(int sv, int dv, int oD) {
        srcVert = sv;
        destVert = dv;
        originalDist = oD;
        distance = oD;
        isEdgeAdjusted = false;
    }

    public void resetEdge() {
        distance = originalDist;
        isEdgeAdjusted = false;
    }

    @Override
    public String toString() {
        return "SettlementEdge{" +
                "srcVert=" + srcVert +
                ", destVert=" + destVert +
                ", distance=" + distance +
                ", isEdgeAdjusted=" + isEdgeAdjusted +
                '}';
    }

}
