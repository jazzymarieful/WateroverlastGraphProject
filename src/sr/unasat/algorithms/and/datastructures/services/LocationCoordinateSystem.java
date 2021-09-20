package sr.unasat.algorithms.and.datastructures.services;

public class LocationCoordinateSystem {

    public boolean checkWithinRange(String weatherstationCoor, String settlementCoor, double range) { //coordinates with format "23,45"
        String[] strArrayWS = weatherstationCoor.split(",");
        String[] strArrayS = settlementCoor.split(",");
        double xCoorWeatherStation = Double.parseDouble(strArrayWS[0]);
        double yCoorWeatherStation = Double.parseDouble(strArrayWS[1]);
        double xCoorSettlement = Double.parseDouble(strArrayS[0]);
        double yCoorSettlement = Double.parseDouble(strArrayS[1]);

        double xCoorPositiveRange = xCoorWeatherStation + range;
        double xCoorNegativeRange = xCoorWeatherStation - range;
        double yCoorPositiveRange = yCoorWeatherStation + range;
        double yCoorNegativeRange = yCoorWeatherStation - range;

        if ((xCoorSettlement > xCoorPositiveRange || xCoorSettlement < xCoorNegativeRange) ||
                (yCoorSettlement > yCoorPositiveRange || yCoorSettlement < yCoorNegativeRange)) {
            return false;
        } else {
            return true;
        }
    }
}
