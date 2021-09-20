package sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph;

import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;

public class WeatherStationVertex {

    public Weatherstation weatherstation;
    private boolean wasVisited;

    public WeatherStationVertex(Weatherstation weatherstation) {
        this.weatherstation = weatherstation;
        wasVisited = false;
    }
}
