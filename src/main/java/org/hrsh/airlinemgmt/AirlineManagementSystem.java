package org.hrsh.airlinemgmt;

import java.util.ArrayList;
import java.util.List;

public class AirlineManagementSystem {
    /**
     * 1. Multiple Aircraft, flights, passengers, staff & crew members, pilots
     * 2. Admin can schedule & cancel flights, add, update aircraft, manage staff, crew, pilots
     * 3. User can reserve 1 or 1+ seats and book a flight
     * 4. User can search flights by date, source/dest airport
     * 5. System can notify date/time changes, flight status
    */

    private List<Aircraft> aircraftList;
    private List<Flight> flightList;
    private List<Staff> staffList;
    private Admin admin;
    private FlightSearchService flightSearchService;
    private FlightBookingService flightBookingService;

    public AirlineManagementSystem() {
        this.aircraftList = new ArrayList<>();
        this.flightList = new ArrayList<>();
        this.staffList = new ArrayList<>();
        this.admin = new Admin();
        this.flightSearchService = new FlightSearchService();
        this.flightBookingService = new FlightBookingService();
    }
}
