package sr.unasat.algorithms.and.datastructures.locations;

import java.util.Arrays;

public class Settlement {

    private String name;
    private int population;
    private String location;
    private Weatherstation associatedWeatherStation;
    private boolean alarmState = false;
    public static int settlementAmount;

    private int evacuatedPopulation;
    private String[] evacuatedSettlements;
    private int evacuatedSettlementsCount = 0;
    private boolean isStranded = false;

    private String safeSettlement;
    private boolean isEvacuated = false;


    public Settlement(String name, int population, String location) {
        settlementAmount++;
        this.name = name;
        this.population = population;
        this.location = location;
        evacuatedSettlements = new String[/*Settlement.settlementAmount*/23]; // opletten hierop
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        isEvacuated = true;
        return population;
    }

    public String getLocation() {
        return location;
    }

    public Weatherstation getAssociatedWeatherStation() {
        return associatedWeatherStation;
    }

    public void setAssociatedWeatherStation(Weatherstation associatedWeatherStation) {
        this.associatedWeatherStation = associatedWeatherStation;
    }

    public boolean getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(boolean set) {
        alarmState = set;
    }

    public int getEvacuatedPopulation() {
        return evacuatedPopulation;
    }

    public void setEvacuatedPopulation(int evacuatedPopulation, Settlement evacuatedSettlement) {
        this.evacuatedPopulation += evacuatedPopulation;
        evacuatedSettlements[evacuatedSettlementsCount++] = evacuatedSettlement.getName();
    }

    public boolean isEvacuated() {
        return isEvacuated;
    }

    public String getEvacuatedSettlements() {
        String evacuatedSettlementsList = "";
        int count = 0;
        evacuatedSettlementsList = evacuatedSettlements[count];
        while (evacuatedSettlements[++count] != null) {
            evacuatedSettlementsList += ", ";
            evacuatedSettlementsList += evacuatedSettlements[count];
        }
        return evacuatedSettlementsList;
    }

    public void setEvacuatedSettlements(String[] evacuatedSettlements) {
        this.evacuatedSettlements = evacuatedSettlements;
    }

    public String getSafeSettlement() {
        return safeSettlement;
    }

    public void setSafeSettlement(Settlement safeSettlement) {
        if (safeSettlement.getName().equals(name)) {
            isStranded = true;
        }
        this.safeSettlement = safeSettlement.getName();
    }

    public boolean isStranded() {
        return isStranded;
    }

    public void resetSettlement() {
        evacuatedPopulation = 0;
        isEvacuated = false;
        Arrays.fill(evacuatedSettlements,null);
        evacuatedSettlementsCount = 0;
        isStranded = false;
    }

    @Override
    public String toString() {
        return "Settlement{" +
                "name='" + name + '\'' +
                ", associatedWeatherStation=" + associatedWeatherStation +
                ", alarmState=" + alarmState +
                '}';
    }

    public String toString2() {
        return "Settlement{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", alarmState=" + alarmState +
                ", evacuatedPopulation=" + evacuatedPopulation +
                '}';
    }
}
