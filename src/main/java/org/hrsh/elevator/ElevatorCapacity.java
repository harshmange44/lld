package org.hrsh.elevator;

public class ElevatorCapacity {
    private int noOfPeople;
    private double weight;

    public ElevatorCapacity(int noOfPeople, double weight) {
        this.noOfPeople = noOfPeople;
        this.weight = weight;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
