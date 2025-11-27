package org.hrsh.elevator;

import java.util.ArrayDeque;
import java.util.Deque;

public class Elevator {
    private String id;
    private int currentFloor = 1;
    private ElevatorCapacity elevatorCapacity = new ElevatorCapacity(8, 950);
    private Direction direction = Direction.IDEAL;
    private EmergencyContact emergencyContact;
    private ElevatorStatus elevatorStatus = ElevatorStatus.AVAILABLE;
    private TowerController towerController;
    private Deque<ElevatorRequest> elevatorRequests = new ArrayDeque<>();

    public void run() {
        processRequests();
    }

    public synchronized void addRequest(ElevatorRequest elevatorRequest) {
        elevatorRequests.offer(elevatorRequest);
        notifyAll();
    }

    public synchronized void processRequests() {
        while (true) {
            while (!elevatorRequests.isEmpty()) {
                processRequest(elevatorRequests.poll());
            }

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(ElevatorRequest elevatorRequest) {
        int noOfPeople = 5;
        int totalWeight = 550;

        if (!isValidRequestCapacity(noOfPeople, totalWeight)) return;

        int sourceFloor = elevatorRequest.getCurrentFloor();
        int targetFloor = elevatorRequest.getDestinationFloor();

        if (sourceFloor < targetFloor) {
            setDirection(Direction.UP);

            for (int currFloor = sourceFloor; currFloor <= targetFloor; currFloor++) {
                setCurrentFloor(currFloor);
            }
        } else {
            setDirection(Direction.DOWN);

            for (int currFloor = sourceFloor; currFloor >= targetFloor; currFloor--) {
                setCurrentFloor(currFloor);
            }
        }
    }

    private boolean isValidRequestCapacity(int totalPassengers, int totalWeight) {
        if (totalPassengers > elevatorCapacity.getNoOfPeople() || totalWeight > elevatorCapacity.getWeight()) return false;

        return true;
    }

    public String getId() {
        return id;
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public ElevatorCapacity getElevatorCapacity() {
        return elevatorCapacity;
    }

    public synchronized Direction getDirection() {
        return direction;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public TowerController getTowerController() {
        return towerController;
    }

    public synchronized ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public synchronized void setElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }
}
