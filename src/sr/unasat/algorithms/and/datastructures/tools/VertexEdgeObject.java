package sr.unasat.algorithms.and.datastructures.tools;

public class VertexEdgeObject {

    public int vertexIndex;
    public int edgeNumber;

    public VertexEdgeObject(int vertexIndex, int edgeNumber) {
        this.vertexIndex = vertexIndex;
        this.edgeNumber = edgeNumber;
    }

    @Override
    public String toString() {
        return "QueObject{" +
                "vertexIndex=" + vertexIndex +
                '}';
    }
}
