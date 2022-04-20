package uebung2;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Main
{
    public static void main(String[] args)
    {
	    // write your code here
        int nrOfVehicles = 60;
        int nrOfMechanics = 2;
        int averageDaysToRepair = 20;
        double averageRepairTimeInDays = 0.5;

        var system_2mech = new BusQueuingSystem(nrOfVehicles,
                nrOfMechanics,
                averageDaysToRepair,
                averageRepairTimeInDays);

        System.out.println(system_2mech);

        nrOfMechanics = 3;
        var system_3mech = new BusQueuingSystem(nrOfVehicles,
                nrOfMechanics,
                averageDaysToRepair,
                averageRepairTimeInDays);

        System.out.println(system_3mech);

        double[][] pis = {
                system_2mech.getPis(),
                system_3mech.getPis()
        };

        String[] seriesNames = {
                "Nr of Mechanics: " + system_2mech.getNrOfMechanics(),
                "Nr of Mechanics: " + system_3mech.getNrOfMechanics()
        };

        plotPIs(pis, seriesNames);
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
