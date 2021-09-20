package sr.unasat.algorithms.and.datastructures.locations;

public class Weatherstation {

    private String name;
    private double rainfall;
    private String location;
    private boolean alarmThresholdBreach = false;
    public static int weatherStationAmount;

    private double infiltrationStandard = 3.0;
    private int timeInterval;
    private int infiltrationInterval = 10;
    private boolean beginInfiltrationInterval = false;
    private int dryInterval = 12;
    private int tempDryInterval;

    private int rainInterval = 0;
    private int rainDuration;
    private boolean isRainDurationSet = false;

    private int dryInterval2 = 0;
    private boolean isDryDurationSet = true;

    public Weatherstation(String name, String location) {
        weatherStationAmount++;
        this.name = name;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAlarmThresholdBreach() {
        return alarmThresholdBreach;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
        checkRainfallThreshold();
    }

    public void checkRainfallThreshold() {
        if (rainfall > 30.0) {
            alarmThresholdBreach = true;
        } else {
            alarmThresholdBreach = false;
        }
    }







    //    public void setRainfallAdvanced(double incomingRainfall) {
//        double actualIncoming = 0.0;
//        if (timeInterval <= dryInterval) {
//            tempDryInterval++;
//            actualIncoming = incomingRainfall;
//        } else if (timeInterval > dryInterval) {
//            tempDryInterval++;
//            if (tempDryInterval == dryInterval + 12) {
//                dryInterval += 24;
//            }
//            actualIncoming = 0.0;
//        }
//        double currentRainfall = rainfall + actualIncoming;
//        timeInterval++;
//        if (timeInterval == infiltrationInterval) {
//            infiltrationInterval += 3;
//            this.rainfall = currentRainfall - infiltrationStandard;
//            checkRainfallThreshold();
//        } else {
//            this.rainfall = currentRainfall;
//            checkRainfallThreshold();
//        }

//    }
//    public void setRainLevel(double incomingRainfall) {
//        double actualIncoming;
//        if (checkDryOrRain()) {
//            actualIncoming = 0.0;
//        } else {
//            actualIncoming = incomingRainfall;
//        }
//        double currentRainfall = rainfall + actualIncoming;
//        timeInterval++;
//        if (timeInterval == 12) {
//            beginInfiltrationInterval = true;
//        }
//        if (beginInfiltrationInterval && (timeInterval == infiltrationInterval)) {
//            infiltrationInterval += 2;
//            this.rainfall =currentRainfall - infiltrationStandard;
//            checkRainfallThreshold();
//        } else {
//            this.rainfall = currentRainfall;
//            checkRainfallThreshold();
//        }
//
//    }
//    public boolean checkDryOrRain() {
//        if (!isDryDurationSet) {  // is false
//            if (!isRainDurationSet) { //is false
//                rainDuration = 5;
//                isRainDurationSet = true;
//            }
//            if (rainInterval == rainDuration) {
//                rainInterval = 0;
//                isRainDurationSet = false;
//                isDryDurationSet = true;
//            } else {
//                rainInterval++;
//                return false;
//            }
//        }
//        if (dryInterval2 == 12) {
//            dryInterval2 = 0;
//            isDryDurationSet = false;
//            isRainDurationSet = true;
//        } else {
//            dryInterval2++;
//            return true;
//        }
//        return false;
//
//    }

    @Override
    public String toString() {
        return "Weatherstation{" +
                "name='" + name + '\'' +
                ", alarmThresholdBreach=" + alarmThresholdBreach +
                '}';
    }
}
