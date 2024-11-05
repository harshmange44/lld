package org.hrsh.airlinemgmt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlightBookingService {
    private final Map<String, Booking> pnrBookingMap;
    private final Object object;

    public FlightBookingService() {
        this.pnrBookingMap = new HashMap<>();
        this.object = new Object();
    }

    public Booking createBooking(Flight flight, Map<Seat, User> seatUserMap) {
        String pnr = UUID.randomUUID().toString();
        Booking booking = new Booking(pnr, flight, seatUserMap, 0, BookingStatus.PENDING);

        synchronized (object) {
            pnrBookingMap.put(pnr, booking);
        }

        return booking;
    }

    public boolean cancelBooking(String pnr) {
        synchronized (object) {
            Booking booking = pnrBookingMap.get(pnr);
            return booking.updateBookingStatus(BookingStatus.CANCELLED).getBookingStatus() == BookingStatus.CANCELLED;
        }
    }
}
