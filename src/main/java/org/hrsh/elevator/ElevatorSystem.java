package org.hrsh.elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorSystem {
    /**
     * Requirements:
     *  1. Multiple Elevators - parallel Operating
     *  2. Assign Ideal/Optimal Elevator - Close to current request floor will be considered optimal
     *  3. Emergency Contact
     *  4. Elevator Capacity validation
     *  5. Direction - IDEAL, UP & DOWN
     *  6. Tower wise controller
     */

    private int noOfElevators;
    private List<Elevator> elevatorList;

    public ElevatorSystem(int noOfElevators) {
        noOfElevators = noOfElevators;
        elevatorList = new ArrayList<>(noOfElevators);

        for (int i = 0; i < noOfElevators; i++) {
            elevatorList.add(new Elevator());
        }

        init();
    }

    public void init() {
        for (Elevator elevator : elevatorList) {
            new Thread(elevator::run).start();
        }
    }

    public void requestElevator(int sourceFloor, int destinationFloor) {
        Elevator optimalElevator = findOptimalElevator(sourceFloor, destinationFloor);
        optimalElevator.addRequest(new ElevatorRequest(sourceFloor, destinationFloor));
    }

    protected Elevator findOptimalElevator(int sourceFloor, int destinationFloor) {

        int minDistance = Integer.MAX_VALUE;
        Elevator optimalElevator = elevatorList.get(0);

        boolean isDirectionUp = sourceFloor < destinationFloor;

        for (Elevator elevator : elevatorList) {
            if (elevator.getElevatorStatus() == ElevatorStatus.AVAILABLE &&
                    (
                            elevator.getDirection() == Direction.IDEAL ||
                                    (isDirectionUp ?
                                    elevator.getDirection() == Direction.UP : elevator.getDirection() == Direction.DOWN)
                    )
            ) {
                int distance = Math.abs(elevator.getCurrentFloor() - sourceFloor);
                if (distance < minDistance) {
                    minDistance = distance;
                    optimalElevator = elevator;
                }
            }
        }

        return optimalElevator;
    }
}
