package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

import sr.unasat.algorithms.and.datastructures.locations.Settlement;

public class SettlementVertex {

    public Settlement settlement;
    public boolean wasVisited;
    public boolean isInPath;

    public SettlementVertex(Settlement settlement) {
        this.settlement = settlement;
        this.isInPath = false;
    }

    @Override
    public String toString() {
        return "SettlementVertex{" +
                "settlement=" + settlement +
                ", wasVisited=" + wasVisited +
                ", isInPath=" + isInPath +
                '}';
    }
}
