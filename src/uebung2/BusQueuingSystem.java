package uebung2;

//M/M/NrOfMechanics/GD/NrOfVehicles/Population NrOfMechanics
public class BusQueuingSystem
{
    private double lambda; //Ankunftsrate
    private double mu; // Bedienungsrate
    private double mus[]; //Bedienugsraten
    private double roh; //traffic intensity
    private int nrOfMechanics;
    private int nrOfVehicles;

    private double averageNumberOfVehiclesInSystem;
    private double averageNumberOfVehiclesInQueue;
    private double averageNumberOfVehiclesInRepair;

    private double averageTimeInSystem;
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
        mus = new double[nrOfVehicles];

        for(int i = 0; i < mus.length; i++)
        {
            if(i < nrOfMechanics - 1)
                mus[i] = (i + 1) * mu;
            else
                mus[i] = nrOfMechanics * mu;
        }

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
        averageNumberOfVehiclesInSystem = roh / (1 - roh);
        averageNumberOfVehiclesInQueue = Math.pow(roh, 2) / (1 - roh);
        averageNumberOfVehiclesInRepair = roh;
    }

    private void calculateTimes()
    {
        averageTimeInSystem = 1/(mu - lambda);
        averageTimeInQueue = lambda/(mu * (mu * lambda));
        averageTimeInRepair = 1 / mu;
    }

    public double getAverageNumberOfVehiclesInQueue() {
        return averageNumberOfVehiclesInQueue;
    }

    public double getAverageNumberOfVehiclesInSystem(){
        return averageNumberOfVehiclesInSystem;
    }

    public double getAverageNumberOfVehiclesInRepair(){
        return averageNumberOfVehiclesInRepair;
    }

    public double getAverageTimeInSystem(){
        return averageTimeInSystem;
    }

    public double getAverageTimeInQueue() {
        return averageTimeInQueue;
    }

    public double getAverageTimeInRepair() {
        return averageTimeInRepair;
    }

    public double[] getPis()
    {
        return pis;
    }

    @Override
    public String toString()
    {
        String result = "";
        result += "Average Number of Vehicles in System: " + averageNumberOfVehiclesInSystem + "\n";
        result += "Average Number of Vehicles in Queue: " + averageNumberOfVehiclesInQueue + "\n";
        result += "Average Number of Vehicles in Repair: " + averageNumberOfVehiclesInRepair + "\n";
        result += "Average Time in System: " + averageTimeInSystem + "\n";
        result += "Average Time in Queue: " + averageTimeInQueue + "\n";
        result += "Average Time in Repair: " + averageTimeInRepair + "\n";

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
