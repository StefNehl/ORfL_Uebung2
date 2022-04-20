package uebung2;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

//M/M/NrOfMechanics/GD/NrOfVehicles/Population NrOfMechanics
public class BusQueuingSystem
{
    private double lambda; //Ankunftsrate
    private double averageLambda;
    private double mu; // Bedienungsrate
    private double roh; //traffic intensity
    private int nrOfMechanics;
    private int nrOfVehicles;

    private double averageNumberOfVehiclesInQueue;
    private double averageNumberOfVehiclesInRepair;

    private double averageTimeInQueue;
    private double averageTimeInRepair;

    private double[] pis;

    public BusQueuingSystem(int nrOfVehicles,
                            int nrOfMechanics,
                            int averageDaysToRepair,
                            double averageRepairTimeInDays)
    {
        this.nrOfMechanics = nrOfMechanics;
        this.nrOfVehicles = nrOfVehicles;

        lambda = 1/(double)averageDaysToRepair;
        mu = 2;//(this.nrOfVehicles - this.nrOfMechanics) * lambda;

        roh = lambda / mu;

        //States in queue
        pis = new double[nrOfVehicles + 1];
        calculatePIs();

        calculateNumberOfVehicles();
        calculateTimes();
    }

    private void calculatePIs()
    {
        //calculate pis  without pi[0]
        var sumWithoutPi0 = 0.0;
        for(int i = 1; i <= nrOfMechanics; i++)
        {
            var result = calculateBinom(nrOfVehicles, i) * Math.pow(roh, i);
            pis[i] = result;
            sumWithoutPi0 += result;
        }

        //calculate pi[0]
        pis[0] = 1 / sumWithoutPi0;
        for(int i = 1; i <= nrOfMechanics; i++)
        {
            pis[i] = pis[i] * pis[0];
        }

        for(int i = nrOfMechanics; i < pis.length; i++)
        {
            var result = (calculateBinom(nrOfVehicles, i) * Math.pow(roh, i) * calculateFactorial(i) * pis[0]) /
                    (calculateFactorial(nrOfMechanics) * Math.pow(nrOfMechanics, i - nrOfMechanics));
            pis[i] = result;
        }
    }

    private double calculateBinom(int n, int k )
    {
        return calculateFactorial(n) / (calculateFactorial(k) * calculateFactorial(n - k));
    }

    private double calculateFactorial(int n)
    {
        if (n == 0)
            return 1;
        else
            return(n * calculateFactorial(n-1));
    }

    private void calculateNumberOfVehicles()
    {
        averageNumberOfVehiclesInRepair = 0;

        for(int i = 0; i < pis.length; i++)
            averageNumberOfVehiclesInRepair += pis[i];

        averageNumberOfVehiclesInQueue = nrOfVehicles - averageNumberOfVehiclesInRepair;
    }

    private void calculateTimes()
    {
        for(int i = 0; i < pis.length; i++)
        {
            averageLambda += lambda * (nrOfVehicles - i) * pis[i];
        }

        averageTimeInRepair = averageNumberOfVehiclesInRepair / averageLambda;
        averageTimeInQueue = averageNumberOfVehiclesInQueue / averageLambda;
    }

    public double getAverageNumberOfVehiclesInQueue() {
        return averageNumberOfVehiclesInQueue;
    }

    public double getAverageNumberOfVehiclesInRepair(){
        return averageNumberOfVehiclesInRepair;
    }

    public double getAverageTimeInQueue() {
        return averageTimeInQueue;
    }

    public double getAverageTimeInRepair() {
        return averageTimeInRepair;
    }

    public int getNrOfMechanics()
    {
        return nrOfMechanics;
    }

    public double[] getPis()
    {
        return pis;
    }

    @Override
    public String toString()
    {
        String result = "";
        result += "Number of Mechanics: " + nrOfMechanics;
        result += "Number of Vehicles in System: " + nrOfVehicles + "\n";
        result += "Average Number of Vehicles in Queue: " + averageNumberOfVehiclesInQueue + "\n";
        result += "Average Number of Vehicles in Repair: " + averageNumberOfVehiclesInRepair + "\n";
        result += "Average Time in Queue [days]: " + averageTimeInQueue + "\n";
        result += "Average Time in Repair [days]: " + averageTimeInRepair + "\n";

        result += "\n";
        result += "PIs:\n";

        for(int i = 0; i < pis.length; i++)
        {
            var row = "P_" + i + ": ";
            row += pis[i];
            row += "\n";
            result += row;
        }


        return result;
    }


}
