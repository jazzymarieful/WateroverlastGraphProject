package sr.unasat.algorithms.and.datastructures.graphs.settlementgraph;

import sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph.WeatherStationVertex;
import sr.unasat.algorithms.and.datastructures.locations.Settlement;
import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;
import sr.unasat.algorithms.and.datastructures.tools.*;
import sr.unasat.algorithms.and.datastructures.services.LocationCoordinateSystem;

import java.util.Arrays;

public class SettlementGraph {

    private final int MAX_VERTS;
    private final int INFINITY = 10000;
    private SettlementVertex settlementVertexList[];
    private int adjMatrix[][];
    public SettlementEdge edgeList[];
    private int vertexCount;
    private int edgeListCount;
    public LocationCoordinateSystem lcs;

    private GraphStack stack;
    private GraphQueue queue;
    public EndangeredSettlementQueue esQueue;
    public OneEdgeQueue oneEdgeQueue;
    public VertexByEdgeStep edgeStepList[];
    boolean settlementDangerDisplay = false;

    private int pathCount;
    private DistanceParent shortestPath[];
    private DistanceParent longestPath[];
    private int currentVert;
    private int startToCurrentDistance;
    private PathStack pathStack;

    public SettlementGraph() {
        MAX_VERTS = Settlement.settlementAmount;
        settlementVertexList = new SettlementVertex[MAX_VERTS];
        adjMatrix = new int[MAX_VERTS][MAX_VERTS];
        vertexCount = 0;
        for (int row = 0; row < MAX_VERTS; row++) {
            for (int column = 0; column < MAX_VERTS; column++) {
                adjMatrix[row][column] = INFINITY;
            }
        }
        lcs = new LocationCoordinateSystem();
        stack = new GraphStack();
        queue = new GraphQueue();
        edgeList = new SettlementEdge[50];
        esQueue = new EndangeredSettlementQueue();
        oneEdgeQueue = new OneEdgeQueue();

        pathCount = 0;
        shortestPath = new DistanceParent[MAX_VERTS];
        longestPath = new DistanceParent[MAX_VERTS];
        pathStack = new PathStack();
    }

    public void addVertex(Settlement settlement) {
        settlementVertexList[vertexCount++] = new SettlementVertex(settlement);
    }

    //adds new weatherstation object in weatherstation vertex in weatherstation vertex array
    public void addAllVertices(ObjectQueue objectQueue) {
        while (!objectQueue.isEmpty()) {
            Settlement settlement = (Settlement) objectQueue.remove();
            settlementVertexList[vertexCount++] = new SettlementVertex(settlement);
        }
    }

    // unused
//    public void addEdge(int start, int end, int weight) {
//        adjMatrix[start][end] = weight;
//        adjMatrix[end][start] = weight;
//    }

    public void addEdgeByName(Settlement sourceS, Settlement destinationS, int distance) {
        int indexOfSourceS = findIndexOfItem(sourceS);
        int indexOfDestinationS = findIndexOfItem(destinationS);
        adjMatrix[indexOfSourceS][indexOfDestinationS] = distance;
//        adjMatrix[indexOfDestinationS][indexOfSourceS] = distance;
        edgeList[edgeListCount++] = new SettlementEdge(indexOfSourceS, indexOfDestinationS, distance);
    }

    public void addEdgeByName(String sourceS, String destinationS, int distance) {
        int indexOfSourceS = findIndexOfItem(sourceS);
        int indexOfDestinationS = findIndexOfItem(destinationS);
        adjMatrix[indexOfSourceS][indexOfDestinationS] = distance;
        edgeList[edgeListCount++] = new SettlementEdge(indexOfSourceS, indexOfDestinationS, distance);
    }

    public void displayVertex(int index) {
        System.out.println(settlementVertexList[index].settlement.toString());
    }

    public void displayVertexSettlementName(int index) {
        System.out.println(settlementVertexList[index].settlement.getName());
    }

    public void displayAllVertices() {
        for (SettlementVertex sv : settlementVertexList) {
            if (sv == null) {
                break;
            }
            System.out.println(sv.settlement.toString2());
        }
    }

    public int findIndexOfItem(Settlement searchItem) {
        for (SettlementVertex sv : settlementVertexList) {
            if (sv.settlement == searchItem) {
                return Arrays.asList(settlementVertexList).indexOf(sv);
            }
        }
        return -1;
    }

    public int findIndexOfItem(String searchItem) {
        for (SettlementVertex sv : settlementVertexList) {
            if (sv.settlement.getName().equals(searchItem)) {
                return Arrays.asList(settlementVertexList).indexOf(sv);
            }
        }
        return -1;
    }

    public void visualizeAdjMatrix() {
        System.out.print("                 ");
        int spaceToFill;
        for (SettlementVertex sv : settlementVertexList) {
            System.out.print(sv.settlement.getName());
            System.out.print(" ");
        }
        System.out.println();
        int adjMatrixRow = 0;
        for (SettlementVertex sv : settlementVertexList) {
            String settlementName = sv.settlement.getName();
            System.out.print(settlementName);
            spaceToFill = 17 - settlementName.length();
            for(int i = 0; i < spaceToFill; i++) {
                System.out.print(" ");
            }
            for (int column = 0; column < MAX_VERTS; column++) {
                System.out.print(adjMatrix[adjMatrixRow][column]);
                spaceToFill = (settlementVertexList[column].settlement.getName().length() -
                        String.valueOf(adjMatrix[adjMatrixRow][column]).length()) + 1;
                for(int i = 0; i < spaceToFill; i++) {
                    System.out.print(" ");
                }
            }
            adjMatrixRow++;
            System.out.println();
        }
        System.out.println();
    }

    public void visualizeAdjMatrixAdjusted() {
        System.out.print("                 ");
        int spaceToFill;
        for (SettlementVertex sv : settlementVertexList) {
            System.out.print(sv.settlement.getName());
            System.out.print(" ");
        }
        System.out.println();
        int adjMatrixRow = 0;
        for (SettlementVertex sv : settlementVertexList) {
            String settlementName = sv.settlement.getName();
            System.out.print(settlementName);
            spaceToFill = 17 - settlementName.length();
            for(int i = 0; i < spaceToFill; i++) {
                System.out.print(" ");
            }
            for (int column = 0; column < MAX_VERTS; column++) {
                if (adjMatrix[adjMatrixRow][column] == INFINITY) {
                    System.out.print(adjMatrix[adjMatrixRow][column]);
                    spaceToFill = (settlementVertexList[column].settlement.getName().length() -
                            String.valueOf(adjMatrix[adjMatrixRow][column]).length()) + 1;
                    for (int i = 0; i < spaceToFill; i++) {
                        System.out.print(" ");
                    }
                } else {
                    for (SettlementEdge se : edgeList) {
                        if (se == null) {
                            break;
                        }
                        if (se.srcVert == adjMatrixRow && se.destVert == column) {
                            if (se.isEdgeAdjusted) {
                                System.out.print(adjMatrix[adjMatrixRow][column] + "**");
                                spaceToFill = (settlementVertexList[column].settlement.getName().length() -
                                        String.valueOf(adjMatrix[adjMatrixRow][column]).length() - 2) + 1;
                                for (int i = 0; i < spaceToFill; i++) {
                                    System.out.print(" ");
                                }
                            } else {
                                System.out.print(adjMatrix[adjMatrixRow][column]);
                                spaceToFill = (settlementVertexList[column].settlement.getName().length() -
                                        String.valueOf(adjMatrix[adjMatrixRow][column]).length()) + 1;
                                for(int i = 0; i < spaceToFill; i++) {
                                    System.out.print(" ");
                                }
                            }
                        }
                    }
                }
            }
            adjMatrixRow++;
            System.out.println();
        }
        System.out.println();
    }

    public void linkWeatherStation(Weatherstation weatherstation) {
        for (SettlementVertex sv : settlementVertexList) {
            String weatherStationLocation = weatherstation.getLocation();
            String settlementLocation = sv.settlement.getLocation();
            if (lcs.checkWithinRange(weatherStationLocation,settlementLocation, 0.35)) {
                sv.settlement.setAssociatedWeatherStation(weatherstation);
            }
        }
    }

    // is used everytime rainfallEvent method is used
    public void checkSettlementAlarm() {
//        boolean settlementDangerDisplay = false;
        for (SettlementVertex sv : settlementVertexList) {
            if (sv.settlement.getAssociatedWeatherStation() == null) {
                sv.settlement.setAlarmState(false);
            } else if (sv.settlement.getAssociatedWeatherStation().isAlarmThresholdBreach()) {
                sv.settlement.setAlarmState(true);
                esQueue.insert(sv.settlement);
                if (!settlementDangerDisplay) {
                    System.out.println(sv.settlement.getName() + " is in danger of flooding");
                }
            } else {
                sv.settlement.setAlarmState(false);
            }
        }
        if (!settlementDangerDisplay) {
            System.out.println("");
        }
        settlementDangerDisplay = true;
    }

    public void dfs(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
        stack.push(startingVertexIndex);
        while(!stack.isEmpty()) {
            int unvisitedVertex = getAdjUnvisitedVertex(stack.peek());
            if(unvisitedVertex == -1) {
                stack.pop();
            } else {
                settlementVertexList[unvisitedVertex].wasVisited = true;
                displayVertexSettlementName(unvisitedVertex);
                stack.push(unvisitedVertex);
            }
        }
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
    }

    //shows how many edges apart vertex is from starting vertex
    public void dfsEdgeStepByVertex(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        edgeStepList = new VertexByEdgeStep[MAX_VERTS];
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
//        displayVertex(0);
        stack.push(startingVertexIndex);
        int edgeStepListCount = 0;
        int edgeStep = 1;
        edgeStepList[edgeStepListCount++] = new VertexByEdgeStep(startingVertexIndex, 0);

        while(!stack.isEmpty()) {
            int stackPeek = stack.peek();
            int unvisitedVertex = getAdjUnvisitedVertex(stackPeek);

            if(unvisitedVertex == -1) { //if row has no weights anymore(wasVisited all true) only 1000
                stack.pop();
            } else {
                settlementVertexList[unvisitedVertex].wasVisited = true;
                displayVertexSettlementName(unvisitedVertex);
//                displayVertex(unvisitedVertex);
                for (VertexByEdgeStep vbes : edgeStepList) {
                    if (vbes == null) {
                        break;
                    }
                    if (vbes.vertexIndex == stackPeek) {
                        edgeStepList[edgeStepListCount++] = new VertexByEdgeStep(unvisitedVertex, vbes.edgeStep + 1);
                        System.out.println("Vertex " + settlementVertexList[edgeStepList[edgeStepListCount-1].vertexIndex].settlement.getName() +
                                " is " + edgeStepList[edgeStepListCount-1].edgeStep + " edge(s) apart from starting vertex");
//                        System.out.println(Arrays.toString(edgeStepList));
                    }
                }
                stack.push(unvisitedVertex);
            }
        }
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
    }

    //shows settlement furthest apart from starting vertex by edge step that is not in alarm state
    public Settlement dfsVertexWithFurthestEdge(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        edgeStepList = new VertexByEdgeStep[MAX_VERTS];
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
        stack.push(startingVertexIndex);
        int edgeStepListCount = 0;
//        int edgeStep = 1;
        edgeStepList[edgeStepListCount++] = new VertexByEdgeStep(startingVertexIndex, 0);

        while(!stack.isEmpty()) {
            int stackPeek = stack.peek();
            int unvisitedVertex = getAdjUnvisitedVertex(stackPeek);

            if(unvisitedVertex == -1) { //if row has no weights anymore(wasVisited all true) only 1000
                stack.pop();
            } else {
                settlementVertexList[unvisitedVertex].wasVisited = true;
//                displayVertexSettlementName(unvisitedVertex);
//                displayVertex(unvisitedVertex);
                for (VertexByEdgeStep vbes : edgeStepList) {
                    if (vbes == null) {
                        break;
                    }
                    if (vbes.vertexIndex == stackPeek) {
                        edgeStepList[edgeStepListCount++] = new VertexByEdgeStep(unvisitedVertex, vbes.edgeStep + 1);
//                        System.out.println("Vertex " + settlementVertexList[edgeStepList[edgeStepListCount-1].vertexIndex].settlement.getName() +
//                                " is " + edgeStepList[edgeStepListCount-1].edgeStep + " edge(s) apart from starting vertex");
//                        System.out.println(Arrays.toString(edgeStepList));
                    }
                }
                stack.push(unvisitedVertex);
            }
        }
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
        int highestEdge = 0;
        int highestEdgeVertex = 0;
        for (VertexByEdgeStep vbes : edgeStepList) {
            if (vbes == null) {
                break;
            }
            // criteria verruimen door ook te kijken naar opgetelde weight distance en dat gekozen vertex niet
            // te dichtbij is van starting vertex adhv coordinaten
            if (vbes.edgeStep > highestEdge &&
                    settlementVertexList[vbes.vertexIndex].settlement.getAlarmState() != true
                    //checkt als furthest settlement buiten range is van endangered settlement
                    /*&& !lcs.checkWithinRange(settlementVertexList[startingVertexIndex].settlement.getLocation(),
                    settlementVertexList[vbes.vertexIndex].settlement.getLocation(), 2.0)*/) {
                highestEdge = vbes.edgeStep;
                highestEdgeVertex = vbes.vertexIndex;
            }
        }
        Settlement settlementWithFurthestEdge = settlementVertexList[highestEdgeVertex].settlement;
        if (highestEdgeVertex == 0) {
            System.out.println("Ala sma dedeeeee!!!");
            settlementWithFurthestEdge = startingVertex;
        } else {
            System.out.println("The settlement furthest from " + startingVertex.getName() + " is " + settlementWithFurthestEdge.getName());
        }
        return settlementWithFurthestEdge;
    }

    public void bfs(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
        queue.insert(startingVertexIndex);
        int v2;
        while(!queue.isEmpty()) {
            int v1 = queue.remove();
            while((v2 = getAdjUnvisitedVertex(v1)) != -1) {  //condition met when end of row is reached
                settlementVertexList[v2].wasVisited = true;
                displayVertexSettlementName(v2);
                queue.insert(v2);
            }
        }
        for(int index = 0; index< vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
    }

    // searches for settlement and if found stops search and resets queue en flags,
    // if not found returns null, must be handled in main
    // cannot find starting item
    public Settlement bfsSpecific(Settlement settlement) {
        Settlement targetSettlement = null;
        settlementVertexList[0].wasVisited = true;
        displayVertexSettlementName(0);
        queue.insert(0);
        int destinationVertex;
        breakAllLoops:
        while(!queue.isEmpty()) {
            System.out.println("toast");
            int sourceVertex = queue.remove();
            while((destinationVertex = getAdjUnvisitedVertex(sourceVertex)) != -1) {
                settlementVertexList[destinationVertex].wasVisited = true;
                queue.insert(destinationVertex);

                if (settlementVertexList[destinationVertex].settlement == settlement) {
//                    displayVertex(destinationVertex);
                    targetSettlement = settlementVertexList[destinationVertex].settlement;
                    break breakAllLoops;
                }
            }
        }
        queue.makeEmpty();
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
        return targetSettlement;
    }

    // searches for a settlement and when found stops search loop and returns item
    // if not found returns null, must be handled in main
    // cannot find starting item
    public Settlement bfsStartFinish(Settlement startingVertex, Settlement settlementToFind) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        Settlement targetSettlement = null;
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
        queue.insert(startingVertexIndex);
        int destinationVertex;
        breakAllLoops:
        while(!queue.isEmpty()) {
            queue.showQueue();
            int sourceVertex = queue.remove();
            while((destinationVertex = getAdjUnvisitedVertex(sourceVertex)) != -1) {
                settlementVertexList[destinationVertex].wasVisited = true;
                queue.insert(destinationVertex);
                if (settlementVertexList[destinationVertex].settlement == settlementToFind) {
                    targetSettlement = settlementVertexList[destinationVertex].settlement;
                    break breakAllLoops;
                }
            }
        }
        queue.makeEmpty();
        for(int j = 0; j < vertexCount; j++) {
            settlementVertexList[j].wasVisited = false;
        }
        return targetSettlement;
    }

    // gets all settlements from starting vertex and shows how many edges apart they are
    public void bfsVertexByEdgeAmount(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        VertexEdgeQueue testQueue = new VertexEdgeQueue(); // scope van queue is voorlopig correct
        settlementVertexList[startingVertexIndex].wasVisited = true;
        displayVertexSettlementName(startingVertexIndex);
        testQueue.insert(new VertexEdgeObject(startingVertexIndex, 0));
        int destinationVertex;

        while (!testQueue.isEmpty()) {
//            testQueue.showQueue();
            VertexEdgeObject sourceVertexObject = testQueue.remove();
            int sourceVertex = sourceVertexObject.vertexIndex;
            int edgeNumber = sourceVertexObject.edgeNumber;
//            System.out.println(sourceVertex);

            while ((destinationVertex = getAdjUnvisitedVertex(sourceVertex)) != -1) {
                settlementVertexList[destinationVertex].wasVisited = true;
                displayVertexSettlementName(destinationVertex);
                System.out.println("This settlement is " + (edgeNumber + 1) + " edge(s) separated from start");
                testQueue.insert(new VertexEdgeObject(destinationVertex, edgeNumber + 1));
            }
        }
        testQueue.makeEmpty();
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
    }

    // gets all vertices 1 edge separated from starting vertex and stores them in queue
    public void bfsVertex1EdgeSeparated(Settlement startingVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        VertexEdgeQueue vertexEdgeQueue = new VertexEdgeQueue();
        settlementVertexList[startingVertexIndex].wasVisited = true;
//        displayVertexSettlementName(startingVertexIndex);
        vertexEdgeQueue.insert(new VertexEdgeObject(startingVertexIndex, 0));
        int destinationVertex;

        while (!vertexEdgeQueue.isEmpty()) {
            VertexEdgeObject sourceVertexObject = vertexEdgeQueue.remove();
            int sourceVertex = sourceVertexObject.vertexIndex;
            int edgeNumber = sourceVertexObject.edgeNumber;

            while ((destinationVertex = getAdjUnvisitedVertex(sourceVertex)) != -1) {
                settlementVertexList[destinationVertex].wasVisited = true;
//                displayVertexSettlementName(destinationVertex);
//                System.out.println("This settlement is " + (edgeNumber + 1) + " edge(s) separated from start");
                if (edgeNumber == 0) {
                    oneEdgeQueue.insert(destinationVertex);
                }
                vertexEdgeQueue.insert(new VertexEdgeObject(destinationVertex, edgeNumber + 1));
            }
        }
        vertexEdgeQueue.makeEmpty();
        for(int index = 0; index < vertexCount; index++) {
            settlementVertexList[index].wasVisited = false;
        }
    }

    public int getAdjUnvisitedVertex(int vertex) {
        for(int column = 0; column < vertexCount; column++) {
            if(adjMatrix[vertex][column] != INFINITY && settlementVertexList[column].wasVisited == false) {
                return column;
            }
        }
        return -1;
    }

    // checks weather settlements are in danger state, then calculates the settlements closest to
    // create a danger zone and calculates danger level of danger zone by recalculating the edge
    // weights with respect to the rainfall level. Then initiates evacuateSettlementPopulation
    public void decideToStartEvacuation() {
        // bfs to find settlement one edge away from endangered settlement
        // method to change edge weight between endangered settlements (put method the moment settlement alarms
        // call evacuateSettlementPopulation
    }

    // increases edge weights between endangered settlement and settlement 1 edge seperated by several criteria
    public void setDangerZone() {
        // turns all edges undirected for more efficient bfs
        turnAdjMatrixUndirectedDirected(true);
        while (!esQueue.isEmpty()) {
            // esQueue was populated by checkSettlementAlarm method
            Settlement endangeredSettlement = esQueue.remove();
            int endangeredSettlementIndex = findIndexOfItem(endangeredSettlement);
            // do bfs on vertices with settlements in alarm state and update queue with settlements one edge seperated from endangered settlement
            bfsVertex1EdgeSeparated(endangeredSettlement);
            while (!oneEdgeQueue.isEmpty()) {
                int oneEdgeSeperatedSettlement = oneEdgeQueue.remove();
                // if edge weight of endangered settlement and settlement 1 edge separated < 90
                if (adjMatrix[endangeredSettlementIndex][oneEdgeSeperatedSettlement] < 90) {
                    // if settlement 1 edge separated is also endangered
                    if (settlementVertexList[oneEdgeSeperatedSettlement].settlement.getAlarmState()) {
                        // edge weight between endangered settlement and settlement 1 edge separated is adjusted by full (80)
                        for (SettlementEdge se : edgeList) {
                            if (se == null) {
                                break;
                            } else if ((se.srcVert == endangeredSettlementIndex || se.srcVert == oneEdgeSeperatedSettlement) &&
                                    (se.destVert == endangeredSettlementIndex || se.destVert == oneEdgeSeperatedSettlement) &&
                                    !se.isEdgeAdjusted) {
                                int oldDistance = se.distance;
                                se.distance += 80;
                                se.isEdgeAdjusted = true;
                                System.out.println("The distance(travel time) between " + settlementVertexList[se.srcVert].settlement.getName() +
                                        " and " + settlementVertexList[se.destVert].settlement.getName() + " has been updated " + "(" + oldDistance +
                                        "=>" + se.distance + ")");
                                break;
                            }
                        }
                    } else {
                        // edge weight of endangered settlement and settlement 1 edge separated is adjusted by half (40)
                        for (SettlementEdge se : edgeList) {
                            if (se == null) {
                                break;
                            } else if ((se.srcVert == endangeredSettlementIndex || se.srcVert == oneEdgeSeperatedSettlement) &&
                                    (se.destVert == endangeredSettlementIndex || se.destVert ==  oneEdgeSeperatedSettlement) &&
                                    !se.isEdgeAdjusted) {
                                int oldDistance = se.distance;
                                se.distance += 40;
                                se.isEdgeAdjusted = true;
                                System.out.println("The distance(travel time) between " + settlementVertexList[se.srcVert].settlement.getName() +
                                        " and " + settlementVertexList[se.destVert].settlement.getName() + " has been updated " + "(" + oldDistance +
                                        "=>" + se.distance + ")");
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("The danger zone(s) have been set");
        oneEdgeQueue.makeEmpty();
        esQueue.makeEmpty();
        turnAdjMatrixUndirectedDirected(false);
        System.out.println("");
    }

    public void turnAdjMatrixUndirectedDirected(boolean undirected) {
        if (undirected) {
            for (int row = 0; row < MAX_VERTS; row++) {
                for (int column = 0; column < MAX_VERTS; column++) {
                    if (adjMatrix[row][column] != INFINITY) {
                        adjMatrix[column][row] = adjMatrix[row][column];
                    }
                }
            }
        } else {
            for (int row = 0; row < MAX_VERTS; row++) {
                for (int column = 0; column < MAX_VERTS; column++) {
                    adjMatrix[row][column] = INFINITY;
                }
            }
            for (int count = 0; count < edgeListCount; count++) {
                if (edgeList[count] == null) {
                    break;
                }
                adjMatrix[edgeList[count].srcVert][edgeList[count].destVert] = edgeList[count].distance;
            }
        }
    }

    // checks which settlements are in danger and calculates the safest settlement the furthest away from the danger zone
    // Then calculates the shortest path and longest path for the population to travel to that safest settlement
    // Removes the population from endangered settlement and adds it to safest settlement population
    public void evacuateSettlementPopulation() {
        checkSettlementAlarm();
        while (!esQueue.isEmpty()) {
            Settlement endangeredSettlement = esQueue.remove();
            // dfs to find settlement safest and furthest away from endangered settlement by several criteria
            Settlement furthestSafeSettlement = dfsVertexWithFurthestEdge(endangeredSettlement);
            // shortestpath from endangered settlement to safe settlement
            int shortestPathDistance = shortestPath(endangeredSettlement, furthestSafeSettlement);
            // longestpath from endangered settlement to safe settlement
            int longestPathDistance = longestPath(endangeredSettlement, furthestSafeSettlement);
            if (longestPathDistance > 800 && shortestPathDistance > 700) {
                System.out.println("The travel cost for the shortest and longest route are too similar to be efficient");
                System.out.println("Therefore the population will be evacuated to the capitol");
                moveSettlementPopulation(endangeredSettlement, settlementVertexList[23].settlement);
            } else {
                moveSettlementPopulation(endangeredSettlement, furthestSafeSettlement);
            }
        }
    }

    // evacuates settlement population en relocates population after danger
    public void moveSettlementPopulation(Settlement endangeredSettlement, Settlement safeSettlement) {
        int endangeredSettlementPop = endangeredSettlement.getPopulation();
        System.out.println("The population of " + endangeredSettlement.getName() + "(" + endangeredSettlementPop + ")" + " has been relocated to " + safeSettlement.getName());
        endangeredSettlement.setSafeSettlement(safeSettlement);
        safeSettlement.setEvacuatedPopulation(endangeredSettlementPop, endangeredSettlement);
        System.out.println("");
    }

    // calculates shortest path from endangered settlement to destination settlement via a path of settlements and
    // displays this path of settlements and returns the total distance to travel
    public int shortestPath(Settlement startingSettlement, Settlement destinationSettlement) {
        int startingVertexIndex = findIndexOfItem(startingSettlement);
        adjustVertexListAdjMatrix(startingVertexIndex, true);

        settlementVertexList[0].isInPath = true;
        pathCount = 1;
        for(int index = 0; index < vertexCount; index++) {  // bij vorige versie getMinD method aanpassing kijk om deze for loop ook aan te passen
            int tempDistance = adjMatrix[0][index];
            shortestPath[index] = new DistanceParent(0, tempDistance);
        }

        while(pathCount < vertexCount) {
            int indexOfVertexWithMinDistance = getIndexOfVertexWithMinDistance();
            int minDistance = shortestPath[indexOfVertexWithMinDistance].distance;
            if (minDistance == INFINITY) {
                System.out.println("There are unreachable settlements");
                break;
            } else {
                currentVert = indexOfVertexWithMinDistance;
                startToCurrentDistance = shortestPath[indexOfVertexWithMinDistance].distance;
            }
            settlementVertexList[currentVert].isInPath = true;
            pathCount++;
            adjustShortestPath();
        }
        displayPath(startingSettlement, destinationSettlement);
        pathCount = 0;
        for(int index = 0; index< vertexCount; index++) {
            settlementVertexList[index].isInPath = false;
        }
        adjustVertexListAdjMatrix(startingVertexIndex, false);
        return shortestPath[findIndexOfItem(destinationSettlement)].distance;
    }

    public int getIndexOfVertexWithMinDistance() {
        int minDistance = INFINITY;
        int vertexIndexWithMinDistance = 0;
        for(int column = 1; column < vertexCount; column++) {
            if(!settlementVertexList[column].isInPath && (shortestPath[column].distance < minDistance)) {
                minDistance = shortestPath[column].distance;
                vertexIndexWithMinDistance = column;
            }
        }
        return vertexIndexWithMinDistance;
    }

    public void adjustShortestPath() {
        int column = 1;
        while(column < vertexCount) {
            if( settlementVertexList[column].isInPath) {
                column++;
                continue;
            }
            int currentToFringeDistance = adjMatrix[currentVert][column];
            int startToFringeDistance = startToCurrentDistance + currentToFringeDistance;
            int shortestPathDist = shortestPath[column].distance;

            if(startToFringeDistance < shortestPathDist) {
                shortestPath[column].parentVert = currentVert;
                shortestPath[column].distance = startToFringeDistance;
            }
            column++;
        }
    }

    // swaps vertex to be used as starting vertex with vertex at beginning of vertexList array and adjMatrix
    // method can unswap vertices aswell
    public void adjustVertexListAdjMatrix(int vertexToSwap, boolean adjust) {
        if (adjust) {
            SettlementVertex tempHolderVertex = settlementVertexList[vertexToSwap];
            settlementVertexList[vertexToSwap] = settlementVertexList[0];
            settlementVertexList[0] = tempHolderVertex;

            for (int columnCount = 0; columnCount < vertexCount; columnCount++) {
                int tempHolderWeight = adjMatrix[vertexToSwap][columnCount];
                adjMatrix[vertexToSwap][columnCount] = adjMatrix[0][columnCount];
                adjMatrix[0][columnCount] = tempHolderWeight;
            }
            for (int rowCount = 0; rowCount < vertexCount; rowCount++) {
                int tempHolderWeight = adjMatrix[rowCount][vertexToSwap];
                adjMatrix[rowCount][vertexToSwap] = adjMatrix[rowCount][0];
                adjMatrix[rowCount][0] = tempHolderWeight;
            }
//            visualizeAdjMatrix();
        } else {
            SettlementVertex tempHolderVertex = settlementVertexList[vertexToSwap];
            settlementVertexList[vertexToSwap] = settlementVertexList[0];
            settlementVertexList[0] = tempHolderVertex;

            for (int columnCount = 0; columnCount < vertexCount; columnCount++) {
                int tempHolderWeight = adjMatrix[0][columnCount];
                adjMatrix[0][columnCount] = adjMatrix[vertexToSwap][columnCount];
                adjMatrix[vertexToSwap][columnCount] = tempHolderWeight;
            }
            for (int rowCount = 0; rowCount < vertexCount; rowCount++) {
                int tempHolderWeight = adjMatrix[rowCount][0];
                adjMatrix[rowCount][0] = adjMatrix[rowCount][vertexToSwap];
                adjMatrix[rowCount][vertexToSwap] = tempHolderWeight;
            }
//            visualizeAdjMatrix();
        }
    }

    public void displayAllPaths() {
        for(int j = 0; j < vertexCount; j++) {
            System.out.print(settlementVertexList[j].settlement.getName() + " = ");
            if(shortestPath[j].distance == INFINITY) {
                System.out.print("inf");
            }
            else {
                System.out.print(shortestPath[j].distance);
            }
            String parent = settlementVertexList[shortestPath[j].parentVert].settlement.getName();
            System.out.print("(" + parent + ") ");
        }
        System.out.println("");
    }

    // displays result of path traveled from starting settlement to destination settlement via settlements
    public void displayPath(Settlement startingVertex, Settlement destinationVertex) {
        for(int index = 0; index < vertexCount; index++) {
            if (index == findIndexOfItem(destinationVertex)) {
                System.out.print("To travel from " + startingVertex.getName() + " to ");
                pathStack.push(settlementVertexList[index].settlement);         // pathstack kijken of het data behoudt
                System.out.print(settlementVertexList[index].settlement.getName() + " through the shortest route");
                System.out.print(" with a cost of ");
                if(shortestPath[index].distance == INFINITY) {
                    System.out.print("0, ");
                }
                else {
                    System.out.print(shortestPath[index].distance + ", ");
                }
//            System.out.print(" travel via ");
                int parentVert = shortestPath[index].parentVert;
                Settlement parent = settlementVertexList[parentVert].settlement;
                pathStack.push(parent);
//            System.out.print(parent);
                int parentOf = parentVert;
                boolean notStartingVertex = true;
                while (notStartingVertex) {
//                System.out.print(" after traveling via ");
                    int temp = shortestPath[parentOf].parentVert;
                    Settlement parentOfParent = settlementVertexList[shortestPath[parentOf].parentVert].settlement;
                    pathStack.push(parentOfParent);
//                System.out.print(parentOfParent + " ");
                    parentOf = temp;
                    if (parentOfParent == startingVertex)
                        notStartingVertex = false;
                }
                System.out.print("travel from ");
                while (!pathStack.isEmpty()) {
                    System.out.print(pathStack.pop().getName());
                    if (!pathStack.isEmpty()) {
                        System.out.print(" to ");
                    }
                }
                System.out.println("");
            }
        }
    }

    public int longestPath(Settlement startingVertex, Settlement destinationVertex) {
        int startingVertexIndex = findIndexOfItem(startingVertex);
        adjustAdjMatrixForLongestPath(true);
        adjustVertexListAdjMatrix(startingVertexIndex, true);

        settlementVertexList[0].isInPath = true;
        pathCount = 1;
        for(int index = 0; index < vertexCount; index++) {
            int tempDistance = adjMatrix[0][index];
            longestPath[index] = new DistanceParent(0, tempDistance);
        }

        while(pathCount < vertexCount) {
            int indexOfVertexWithMaxDistance = getIndexOfVertexWithMaxDistance();
            int maxDistance = longestPath[indexOfVertexWithMaxDistance].distance;
            if (maxDistance == 0) {
                System.out.println("There are unreachable settlements");
                break;
            } else {
                currentVert = indexOfVertexWithMaxDistance;
                startToCurrentDistance = longestPath[indexOfVertexWithMaxDistance].distance;
            }
            settlementVertexList[currentVert].isInPath = true;
            pathCount++;
            adjustLongestPath();
        }
        displayLongestPath(startingVertex, destinationVertex);
        pathCount = 0;
        for(int index = 0; index< vertexCount; index++) {
            settlementVertexList[index].isInPath = false;
        }
        adjustAdjMatrixForLongestPath(false);
        adjustVertexListAdjMatrix(startingVertexIndex, false);
        return longestPath[findIndexOfItem(destinationVertex)].distance;
    }

    public int getIndexOfVertexWithMaxDistance() {
        int maxDistance = 0;
        int vertexIndexWithMaxDistance = 0;
        for(int column = 1; column < vertexCount; column++) {
            if(!settlementVertexList[column].isInPath && (longestPath[column].distance > maxDistance)) {
                maxDistance = longestPath[column].distance;
                vertexIndexWithMaxDistance = column;
            }
        }
        return vertexIndexWithMaxDistance;
    }

    public void adjustLongestPath() {
        int column = 1;
        while(column < vertexCount) {
            if(settlementVertexList[column].isInPath) {
                column++;
                continue;
            }
            int currentToFringeDistance = adjMatrix[currentVert][column];
            int startToFringeDistance = startToCurrentDistance + currentToFringeDistance;
            int longestPathDist = longestPath[column].distance;

            if(startToFringeDistance > longestPathDist && currentToFringeDistance != 0) {
                longestPath[column].parentVert = currentVert;
                longestPath[column].distance = startToFringeDistance;
            }
            column++;
        }
    }

    public void displayAllLongestPaths() {
        for(int j = 0; j < vertexCount; j++) {
            System.out.print(settlementVertexList[j].settlement.getName() + "=");
            if(longestPath[j].distance == 0) {
                System.out.print("inf");
            }
            else {
                System.out.print(longestPath[j].distance);
            }
            String parent = settlementVertexList[longestPath[j].parentVert].settlement.getName();
            System.out.print("(" + parent + ") ");
        }
        System.out.println("");
    }

    public void adjustAdjMatrixForLongestPath(boolean adjust) {
        if (adjust) {
            for(int row = 0; row < MAX_VERTS; row++) {
                for(int column = 0; column < MAX_VERTS; column++) {
                    if (adjMatrix[row][column] == INFINITY) {
                        adjMatrix[row][column] = 0;
                    }
                }
            }
        } else {
            for(int row = 0; row < MAX_VERTS; row++) {
                for(int column = 0; column < MAX_VERTS; column++) {
                    if (adjMatrix[row][column] == 0) {
                        adjMatrix[row][column] = INFINITY;
                    }
                }
            }
        }
    }

    public void displayLongestPath(Settlement startingVertex, Settlement destinationVertex) {
        for(int index = 0; index < vertexCount; index++) {
            if (index == findIndexOfItem(destinationVertex)) {
                System.out.print("To travel from " + startingVertex.getName() + " to ");
                pathStack.push(settlementVertexList[index].settlement);
                System.out.print(settlementVertexList[index].settlement.getName() + " through the longest route");
                System.out.print(" with a cost of ");
                if(longestPath[index].distance == INFINITY) {
                    System.out.print("0");
                }
                else {
                    System.out.print(longestPath[index].distance + ", ");
                }
//            System.out.print(" travel via ");
                int parentVert = longestPath[index].parentVert;
                Settlement parent = settlementVertexList[parentVert].settlement;
                pathStack.push(parent);
//            System.out.print(parent);
                int parentOf = parentVert;
                boolean notStartingVertex = true;
                while (notStartingVertex) {
//                System.out.print(" after traveling via ");
                    int temp = longestPath[parentOf].parentVert;
                    Settlement parentOfParent = settlementVertexList[longestPath[parentOf].parentVert].settlement;
                    pathStack.push(parentOfParent);
//                System.out.print(parentOfParent + " ");
                    parentOf = temp;
                    if (parentOfParent == startingVertex)
                        notStartingVertex = false;
                }
                System.out.print("travel from ");
                while (!pathStack.isEmpty()) {
                    System.out.print(pathStack.pop().getName());
                    if (!pathStack.isEmpty()) {
                        System.out.print(" to ");
                    }
                }
                System.out.println("");
            }
        }
    }

    // gives a report of the displacement of the populations of the settlements before and after the disaster
    public void evacuationReport() {
        System.out.println("This is a report of the displacement of the populations before and after the flooding");
        for (SettlementVertex sv : settlementVertexList) {
            if (sv.settlement.isEvacuated() && !sv.settlement.isStranded()) {
                System.out.println("The population of " + sv.settlement.getName() + " before the evacuation was " + sv.settlement.getPopulation() +
                        " and after " + 0 + " (The population was evacuated to " + sv.settlement.getSafeSettlement() + ")");
            } else if (sv.settlement.isStranded()) {
                System.out.println("The population of " + sv.settlement.getName() + " before the evacuation was " + sv.settlement.getPopulation() +
                        " and after " + 0 + " (The population was not evacuated, it has been stranded)");
            } else if (sv.settlement.getEvacuatedPopulation() == 0) {
                System.out.println("The population of " + sv.settlement.getName() + " before the evacuation was " + sv.settlement.getPopulation() +
                        " and after " + sv.settlement.getPopulation() + " (This settlement was not affected by flooding)");
            } else {
                System.out.println("The population of " + sv.settlement.getName() + " before the evacuation was " + sv.settlement.getPopulation() +
                        " and after " + (sv.settlement.getPopulation() + sv.settlement.getEvacuatedPopulation()) +
                        " (This settlement received evacuees from " + sv.settlement.getEvacuatedSettlements() + ")");
            }
        }
        System.out.println("");
    }

    public void resetEvacuation() {
        //reset edge weights within edgeList
        for (SettlementEdge se : edgeList) {
            if (se == null) {
                break;
            }
            se.resetEdge();
        }
        //reset settlement population evacuation stats
        for (SettlementVertex sv : settlementVertexList) {
            sv.settlement.resetSettlement();
        }
        //reset adjMatrix
        int edgeListCount = 0;
        for (int row = 0; row < MAX_VERTS; row++) {
            for (int column = 0; column < MAX_VERTS; column++) {
                if (!(adjMatrix[row][column] == INFINITY)) {
                    for (SettlementEdge se : edgeList) {
                        if (se == null) {
                            break;
                        } else if (se.srcVert == row && se.destVert == column) {
                            adjMatrix[row][column] = se.originalDist;
                        }
                    }
                } else {
                    adjMatrix[row][column] = INFINITY;
                }
            }
        }
        settlementDangerDisplay = false;
        System.out.println("Evacuation event has been reset");
        System.out.println("");
    }
}
