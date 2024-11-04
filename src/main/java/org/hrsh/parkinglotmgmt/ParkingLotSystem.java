package org.hrsh.parkinglotmgmt;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    // 1. Multiple Parking Lots
    // 2. Parking -> Multiple Floors
    // 3. Multiple Entry & Exit Points
    // 4. Entry Point - Collect Entry Point, Exit Point - Pay at Exit Point
    // 5. Customer Info Portal on each floor - May Pay here as well
    // 6. Payed via Cash, Card, etc.
    // 7. Display Panel at Entry Points
    // 8. Different types of Parking Spot - Compact, Large, etc.
    // 9. Parking Spot should support different types of vehicles - car, truck, etc.
    // 10. Parking Spot for Electric Car - Electric Panel
    // 11. Display Panel on each floor showing Available Parking Spots
    // 12. Per hour parking fee model

    private final List<ParkingLot> parkingLotList;

    public ParkingLotSystem() {
        this.parkingLotList = new ArrayList<>();
    }
}
