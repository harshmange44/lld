package org.hrsh.airlinemgmt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightSearchService {
    List<Flight> flightListIndex;

    public FlightSearchService() {
        this.flightListIndex = new ArrayList<>();
    }

    public List<Flight> searchFlights(Location srcLocation, Location destLocation, LocalDateTime dateTime) {
        return flightListIndex.stream()
                .filter(flight -> (flight.getSrcLocation().getArea().contains(srcLocation.getArea()) ||
                        flight.getSrcLocation().getStreet().contains(srcLocation.getStreet()))
                        && (flight.getDestLocation().getArea().contains(destLocation.getArea()) ||
                        flight.getDestLocation().getStreet().contains(destLocation.getStreet()))
                        && flight.getStartTime().equals(dateTime))
                .collect(Collectors.toList());
    }

    public boolean addFlight(Flight flight) {
        return flightListIndex.add(flight);
    }

    public List<Flight> getFlightListIndex() {
        return flightListIndex;
    }

    public void setFlightListIndex(List<Flight> flightListIndex) {
        this.flightListIndex = flightListIndex;
    }
}
