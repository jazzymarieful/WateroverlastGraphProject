package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

public class VertexByEdgeStep {

    public int vertexIndex;
    public int edgeStep;

    public VertexByEdgeStep(int vertexIndex, int edgeStep) {
        this.vertexIndex = vertexIndex;
        this.edgeStep = edgeStep;
    }

    @Override
    public String toString() {
        return "VertexByEdgeStep{" +
                "vertexIndex=" + vertexIndex +
                ", edgeStep=" + edgeStep +
                '}';
    }
}
