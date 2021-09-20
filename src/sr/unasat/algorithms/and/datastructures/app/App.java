package sr.unasat.algorithms.and.datastructures.app;

import sr.unasat.algorithms.and.datastructures.graphs.settlementgraph.SettlementGraph;
import sr.unasat.algorithms.and.datastructures.graphs.weatherstationgraph.WeatherStationGraph;
import sr.unasat.algorithms.and.datastructures.locations.Settlement;
import sr.unasat.algorithms.and.datastructures.locations.Weatherstation;
import sr.unasat.algorithms.and.datastructures.services.RainfallProducer;
import sr.unasat.algorithms.and.datastructures.tools.ObjectQueue;

public class App {

    public static void main(String[] args) throws InterruptedException {

        //Queue aanmaken om settlement objecten op te slaan
        ObjectQueue settlementsContainer = new ObjectQueue();
        //Region north-center
        settlementsContainer.insert(new Settlement("Pokigron", 2000, "4.53,-55.39"));
        settlementsContainer.insert(new Settlement("Botopasi", 750, "4.22,-55.44"));
        settlementsContainer.insert(new Settlement("Djumu", 1000, "4.01,-55.47"));
        settlementsContainer.insert(new Settlement("Pusugrunu", 600, "4.39,-55.78"));
        settlementsContainer.insert(new Settlement("Nyun JacobKondre", 500, "4.94,-55.54"));
        //Region north-east
        settlementsContainer.insert(new Settlement("Langatabiki", 1100, "4.99,-54.44"));
        settlementsContainer.insert(new Settlement("Stoelmanseiland", 5000, "4.35,-54.40"));
        settlementsContainer.insert(new Settlement("Dritabiki", 500, "4.12,-54.62"));
        settlementsContainer.insert(new Settlement("Godo Olo", 600, "4.06,-54.79"));
        settlementsContainer.insert(new Settlement("Benzdorp", 400, "3.67,-54.09"));
        //Region north-west
        settlementsContainer.insert(new Settlement("Apura", 2400, "5.18,-57.17"));
        settlementsContainer.insert(new Settlement("Avanavero", 350, "4.83,-57.29"));
        settlementsContainer.insert(new Settlement("Kabalebo", 80, "4.41,-57.22"));
        //Region south-west
        settlementsContainer.insert(new Settlement("Kwamalasamutu", 1500, "2.36,-56.79"));
        settlementsContainer.insert(new Settlement("Sipaliwini", 300, "2.02,-56.12"));
        settlementsContainer.insert(new Settlement("Alalapadu", 400, "2.52,-56.33"));
        settlementsContainer.insert(new Settlement("Amotopo", 300, "3.55,-57.64"));
        //Region south-east
        settlementsContainer.insert(new Settlement("Apetina", 800, "3.51,-55.05"));
        settlementsContainer.insert(new Settlement("Palumeu", 900, "3.34,-55.44"));
        settlementsContainer.insert(new Settlement("Peleletepu", 1100, "3.16,-55.72"));
        settlementsContainer.insert(new Settlement("Ulemari", 250, "3.10,-54.54"));
        //Miscellaneous
        settlementsContainer.insert(new Settlement("Tafelberg", 20, "3.79,-56.15"));
        settlementsContainer.insert(new Settlement("Kayserberg", 10, "3.09,-56.47"));
        //Capitol
        settlementsContainer.insert(new Settlement("Paramaribo", 241000, "5.81,-55.17"));

        //Settlement graph aanmaken
        SettlementGraph settlementGraph = new SettlementGraph();

        // Settlements opslaan in settlement graph vertices
        settlementGraph.addAllVertices(settlementsContainer);

        // Settlements verbinden en de afstand(edge weight) onderling bepalen
        settlementGraph.addEdgeByName("Pokigron", "Botopasi",30);
        settlementGraph.addEdgeByName("Pokigron", "Stoelmanseiland",106);
        settlementGraph.addEdgeByName("Pokigron", "Alalapadu",243);
        settlementGraph.addEdgeByName("Pokigron", "Nyun JacobKondre",53);
        settlementGraph.addEdgeByName("Pokigron", "Tafelberg",117);
        settlementGraph.addEdgeByName("Botopasi", "Djumu",23);
        settlementGraph.addEdgeByName("Djumu", "Pokigron",54);
        settlementGraph.addEdgeByName("Djumu", "Apetina",72);
        settlementGraph.addEdgeByName("Pusugrunu", "Botopasi",43);
        settlementGraph.addEdgeByName("Pusugrunu", "Apura",176);
        settlementGraph.addEdgeByName("Nyun JacobKondre", "Pusugrunu",66);

        settlementGraph.addEdgeByName("Stoelmanseiland", "Dritabiki",34);
        settlementGraph.addEdgeByName("Stoelmanseiland", "Benzdorp",84);
        settlementGraph.addEdgeByName("Dritabiki", "Godo Olo",19);
        settlementGraph.addEdgeByName("Godo Olo", "Stoelmanseiland",52);
        settlementGraph.addEdgeByName("Godo Olo", "Djumu",76);
        settlementGraph.addEdgeByName("Langatabiki", "Stoelmanseiland",71);
        settlementGraph.addEdgeByName("Benzdorp", "Dritabiki",78);

        settlementGraph.addEdgeByName("Apura", "Avanavero",42);
        settlementGraph.addEdgeByName("Apura", "Kabalebo",86);
        settlementGraph.addEdgeByName("Kabalebo", "Avanavero",47);
        settlementGraph.addEdgeByName("Kabalebo", "Tafelberg",138);
        settlementGraph.addEdgeByName("Avanavero", "Amotopo",148);

        settlementGraph.addEdgeByName("Kwamalasamutu", "Sipaliwini",81);
        settlementGraph.addEdgeByName("Kwamalasamutu", "Djumu",235);
        settlementGraph.addEdgeByName("Sipaliwini", "Alalapadu",59);
        settlementGraph.addEdgeByName("Alalapadu", "Kwamalasamutu",54);
        settlementGraph.addEdgeByName("Alalapadu", "Amotopo",184);
        settlementGraph.addEdgeByName("Amotopo", "Kwamalasamutu",162);

        settlementGraph.addEdgeByName("Apetina", "Ulemari",72);
        settlementGraph.addEdgeByName("Apetina", "Palumeu",47);
        settlementGraph.addEdgeByName("Palumeu", "Peleletepu",37);
        settlementGraph.addEdgeByName("Peleletepu", "Alalapadu",98);
        settlementGraph.addEdgeByName("Ulemari", "Benzdorp",81);

        settlementGraph.addEdgeByName("Tafelberg", "Kayserberg",85);
        settlementGraph.addEdgeByName("Kayserberg", "Peleletepu",82);

        //Queue aanmaken om weatherstation objecten op te slaan
        ObjectQueue weatherstationContainer = new ObjectQueue();
        weatherstationContainer.insert(new Weatherstation("Apu2", "4.99,-57.20"));      //1
        weatherstationContainer.insert(new Weatherstation("Kaba1", "4.40,-57.22"));     //2
        weatherstationContainer.insert(new Weatherstation("Nyun1", "4.92,-55.54"));     //3
        weatherstationContainer.insert(new Weatherstation("Pusu1", "4.39,-55.79"));     //4
        weatherstationContainer.insert(new Weatherstation("Dju1", "4.00,-55.47"));      //5
        weatherstationContainer.insert(new Weatherstation("Poki2", "4.37,-55.40"));     //6
        weatherstationContainer.insert(new Weatherstation("Benz1", "3.85,-54.22"));     //7
        weatherstationContainer.insert(new Weatherstation("Langa1", "4.99,-54.44"));    //8
        weatherstationContainer.insert(new Weatherstation("Stoel3", "4.26,-54.48"));    //9
        weatherstationContainer.insert(new Weatherstation("Ape3", "3.38,-55.40"));      //10
        weatherstationContainer.insert(new Weatherstation("Ule1", "3.10,-54.54"));      //11
        weatherstationContainer.insert(new Weatherstation("Amo1", "3.54,-57.63"));      //12
        weatherstationContainer.insert(new Weatherstation("Ala2", "2.42,-56.53"));      //13
        weatherstationContainer.insert(new Weatherstation("Sip1", "2.02,-56.12"));      //14
        weatherstationContainer.insert(new Weatherstation("Tafel2", "3.44,-56.28"));    //15

        //Weatherstation graph aanmaken en het associeren met settlement graph
        WeatherStationGraph weatherStationGraph = new WeatherStationGraph(settlementGraph);

        //Weatherstations in weatherstation graph opslaan en elke weatherstation associeren en opslaan
        //bij een settlement a.d.h.v. onderlinge coordinaten m.b.v. linkWeatherstation method
        weatherStationGraph.addAllVertices(weatherstationContainer);

        System.out.println("This is a simulation of a nationwide rainfall occurrence, the flooding of settlements that may occur");
        System.out.println("and the eventual succeeding evacuation process");
        System.out.println();

        // Class aanmaken die zal zorgen voor regenval over de hele regio
        RainfallProducer rp = new RainfallProducer(weatherStationGraph, settlementGraph);

        // Method oproepen die regenval over de hele regio zal produceren m.b.v. de generateRandomRainfall method als argument en
        // die neerslaghoeveelheid per weatherstation update en neerslagoverschijding monitoort en
        // die settlement alarm toestand monitoort a.d.h.v. weatherstation neerslagoverschrijding m.b.v. de checkSettlementAlarm method
        rp.rainfallEvent(rp.generateRandomRainfall());

        // OP GITHUB ZETTEN

        // Overzicht van de edge weights in adjacency matrix in tabelvorm
        settlementGraph.visualizeAdjMatrix();

        // Method oproepen die de settlements achterhaald die in alarmtoestand(wateroverlast) zitten en
        // die een gevaren zone aankaart door de edge weight te verhogen tussen de ondergelopen settlement en
        // settlements die 1 edge verwijdert
        settlementGraph.setDangerZone();

        // Overzicht van de aangepaste edge weight in adjacency matrix in tabelvorm
        settlementGraph.visualizeAdjMatrixAdjusted();

        settlementGraph.evacuateSettlementPopulation();

        settlementGraph.evacuationReport();

        settlementGraph.resetEvacuation();














//        int count = 0;
//        while (count != 200) {
//            rp.rainfallEvent(rp.generateRandomRainfall());
//            settlementGraph.visualizeAdjMatrix();
//            settlementGraph.setDangerZone();
//            settlementGraph.visualizeAdjMatrixAdjusted();
//            settlementGraph.evacuateSettlementPopulation();
//            settlementGraph.evacuationReport();
//            settlementGraph.resetEvacuation();
//            count++;
//            Thread.sleep(1000);
//        }
    }
}
