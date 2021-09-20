package sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph;

import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;
import sr.unasat.algorithms.and.datastructures.graphs.settlementgraph.SettlementGraph;
import sr.unasat.algorithms.and.datastructures.tools.ObjectQueue;

import java.util.Arrays;

public class WeatherStationGraph {

    private final int MAX_VERTS;
    private WeatherStationVertex weatherStationVertexList[];
    private int adjMatrix[][];
    private int vertexCount;
    private SettlementGraph sg;

    public WeatherStationGraph(SettlementGraph sg) {
        MAX_VERTS = Weatherstation.weatherStationAmount;
        weatherStationVertexList = new WeatherStationVertex[MAX_VERTS];
        adjMatrix = new int[MAX_VERTS][MAX_VERTS];
        vertexCount = 0;
        for (int row = 0; row < MAX_VERTS; row++) {
            for (int column = 0; column < MAX_VERTS; column++) {
                adjMatrix[row][column] = 0;
            }
        }
        this.sg = sg;
    }

    public WeatherStationVertex[] getWeatherStationVertexList() {
        return weatherStationVertexList;
    }


//    public void addVertex(Weatherstation weatherstation) {
//        weatherStationVertexList[vertexCount++] = new WeatherStationVertex(weatherstation);
//        sg.linkWeatherStation(weatherstation);
//    }

    //adds new weatherstation object in weatherstation vertex in weatherstation vertex array
    public void addAllVertices(ObjectQueue objectQueue) {
        while (!objectQueue.isEmpty()) {
            Weatherstation weatherstation = (Weatherstation) objectQueue.remove();
            weatherStationVertexList[vertexCount++] = new WeatherStationVertex(weatherstation);
            sg.linkWeatherStation(weatherstation);
        }
    }

    public void addEdgeByName(Weatherstation sourceWS, Weatherstation destinationWS) {
        int indexOfSourceWSV = findIndexOfItem(sourceWS);
        int indexOfDestinationWSV = findIndexOfItem(destinationWS);
        adjMatrix[indexOfSourceWSV][indexOfDestinationWSV] = 1;
        adjMatrix[indexOfDestinationWSV][indexOfSourceWSV] = 1;
    }

    public void connectAllVertices() {
        for (int row = 0; row < MAX_VERTS; row++) {
            for (int column = 0; column < MAX_VERTS; column++) {
                adjMatrix[row][column] = 1;
            }
        }
    }

    public void displayVertex(int index) {
        System.out.println(weatherStationVertexList[index].weatherstation.toString());
    }

    public void displayAllVertices() {
        for (WeatherStationVertex wsv : weatherStationVertexList) {
            if (wsv == null) {
                break;
            }
            System.out.println(wsv.weatherstation.toString());
        }
    }

    public int findIndexOfItem(Weatherstation searchItem) {
        for (WeatherStationVertex wsv : weatherStationVertexList) {
            if (wsv.weatherstation == searchItem) {
                return Arrays.asList(weatherStationVertexList).indexOf(wsv);
            }
        }
        return -1;
    }
}
