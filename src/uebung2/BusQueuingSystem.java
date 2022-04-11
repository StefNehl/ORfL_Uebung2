package uebung2;

//M/M/NrOfMechanics/GD/NrOfVehicles/Population NrOfMechanics
public class BusQueuingSystem
{
    private double lambda; //Ankunftsrate
    private double mu; // Bedienungsrate
    private double roh; //traffic intensity
    private int nrOfMechanics;
    private int nrOfVehicles;

    private double averageNumberOfVehiclesInSystem;
    private double averageNumberOfVehiclesInQueue;
    private double averageNumberOfVehiclesInRepair;

    private double averageTimeInSystem;
    private double averageTimeInQueue;
    private double averageTimeInRepair;

    public BusQueuingSystem(int nrOfVehicles,
                            int nrOfMechanics,
                            int averageDaysToRepair,
                            double averageRepairTimeInDays)
    {
        this.nrOfMechanics = nrOfMechanics;
        this.nrOfVehicles = nrOfVehicles;

        lambda = 1/(double)averageDaysToRepair;
        mu = (this.nrOfVehicles - this.nrOfMechanics) * lambda;
        roh = lambda / mu;

        calculateNumberOfVehicles();
        calculateTimes();
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

        return result;
    }
}
