package uebung2;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        //Example from Book P.1101 (for testing)
        int nrOfVehicles = 5;
        int nrOfMechanics = 2;
        int averageDaysToRepair = 30;
        double averageRepairTimeInDays = 3;

        var system_book = new BusQueuingSystem(nrOfVehicles,
                nrOfMechanics,
                averageDaysToRepair,
                averageRepairTimeInDays);

        System.out.println(system_book);

        //Examples

        int minNumberOfMechanics = 2;
        int maxNumberOfMechanics = 3;
        int numberOfSystems = (maxNumberOfMechanics - minNumberOfMechanics) + 1;

        if(numberOfSystems < 1)
        {
            System.out.println("Check min and max mechanics!");
            return;
        }

        nrOfVehicles = 60;
        averageDaysToRepair = 20;
        averageRepairTimeInDays = 0.5;

        BusQueuingSystem[] systems = new BusQueuingSystem[numberOfSystems];
        double[][] pis = new double[numberOfSystems][nrOfVehicles + 1];
        String[] seriesNames = new String[numberOfSystems];

        for(int i = minNumberOfMechanics; i <= maxNumberOfMechanics; i++)
        {
            nrOfMechanics = i;
            var system = new BusQueuingSystem(nrOfVehicles,
                    nrOfMechanics,
                    averageDaysToRepair,
                    averageRepairTimeInDays);

            System.out.println(system);
            systems[i - minNumberOfMechanics] = system;
            pis[i - minNumberOfMechanics] = system.getPis();
            seriesNames[i - minNumberOfMechanics] = "Nr of Mechanics: " + system.getNrOfMechanics();

        }

        printResult(systems);
        plotPIs(pis, seriesNames);
    }

    public static void printResult(BusQueuingSystem[] systems)
    {
        System.out.println("durchschnittliche Anzahl an Autobussen, die betriesbereit sind:");
        for (BusQueuingSystem system : systems)
        {
            System.out.printf(system.getNrOfMechanics() + " Mechaniker: %.0f ", system.getAverageNumberOfVehiclesInQueue());
            System.out.println();
        }
        System.out.println();

        System.out.println("durchschnittliche Zeit für die Reparatur:");
        for (BusQueuingSystem system : systems)
        {
            System.out.printf(system.getNrOfMechanics() + " Mechaniker: %.4f ", system.getAverageTimeInRepair());
            System.out.println("[Tage]");
        }
        System.out.println();

        System.out.println("Anteil der Zeit ohne Auftrag:");
        for (BusQueuingSystem system : systems)
        {
            System.out.printf(system.getNrOfMechanics() + " Mechaniker: %.2f ", system.getFractionOfIdleTime() * 100);
            System.out.println("[%]");
        }
        System.out.println();
    }

    public static void plotPIs(double[][] pis, String[] seriesNames)
    {
        double[] t = new double[pis[0].length];
        for(int i = 0; i < t.length; i++)
            t[i] = i;

        XYChart chart = QuickChart.getChart("PIs", "Vehicles", "PI(V)", seriesNames, t, pis);
        new SwingWrapper<XYChart>(chart).displayChart();
    }
}
