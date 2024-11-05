package org.hrsh.airlinemgmt;

public class Admin extends User {
    public boolean addAircraft(Aircraft aircraft) {
        return true;
    }

    public boolean editAircraft(String id, Aircraft aircraft) {
        return true;
    }

    public boolean scheduleFlight(Flight flight) {
        return true;
    }

    public boolean cancelFlight(String flightId) {
        return true;
    }

    public boolean addStaff(Staff staff) {
        return true;
    }

    public boolean addPilot(Pilot pilot) {
        return true;
    }

    public boolean assignPilot(Pilot pilot, Flight flight) {
        if (flight.getPilot() == null) {
            flight.setPilot(pilot);
            return true;
        }
        return false;
    }
}
