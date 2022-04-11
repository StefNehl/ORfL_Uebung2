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

        var system = new BusQueuingSystem(nrOfVehicles,
                nrOfMechanics,
                averageDaysToRepair,
                averageRepairTimeInDays);

        System.out.println(system);
    }
}
