package sr.unasat.algorithms.and.datastructures.services;

import sr.unasat.algorithms.and.datastructures.graphs.settlementgraph.SettlementGraph;
import sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph.WeatherStationGraph;
import sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph.WeatherStationVertex;
import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;

import java.util.Arrays;
import java.util.Random;

public class RainfallProducer {

    Random random = new Random();

    private WeatherStationGraph wsg;
    private SettlementGraph sg;

    public RainfallProducer(WeatherStationGraph wsg, SettlementGraph sg) {
        this.wsg = wsg;
        this.sg = sg;
    }

    public double[] generateRandomRainfall() {
        int weatherStationCount = Weatherstation.maxVert;
        double[] rainfallByStation = new double[weatherStationCount];
        int rainfallByStationCount = 0;
        double minAmount = 10.0;
        double maxAmount = 35.0;
        for (int index = 0; index < weatherStationCount; index++) {
            double randomAmount = (random.nextDouble() * (maxAmount - minAmount) + minAmount);
            rainfallByStation[rainfallByStationCount++] = Math.round(randomAmount * 10) / 10.0;
        }
        System.out.println("The rainfall values recorded by the weather stations are " + Arrays.toString(rainfallByStation));
        System.out.println("");
        return rainfallByStation;
    }

    public void rainfallEvent(double[] rainfallByWS) {
        int rainfallPerWSIndex = 0;
        // attach rainfall value of argument sequentially to all weather stations
        for (WeatherStationVertex wsv : wsg.getWeatherStationVertexList()) {
            // updates rainfall value of weather station and checks alarm breach
            wsv.weatherstation.setRainfall(rainfallByWS[rainfallPerWSIndex++]);
        }
        // check all settlement weatherstations voor alarm
        sg.checkSettlementAlarm();
    }

}
