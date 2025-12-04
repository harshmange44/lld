package org.hrsh.hotelmgmt;

public class Server {
    /**
     * Server class for handling room service and notifications
     * Methods are called through HotelManagementSystem
     */

    public void notify(RoomBooking roomBooking, Notification notification) {
        if (roomBooking == null || notification == null) {
            return;
        }
        // Add notification to booking
        roomBooking.getNotifications().add(notification);
    }

    public boolean addRoomCharge(RoomBooking roomBooking, RoomCharge roomCharge) {
        if (roomBooking == null || roomCharge == null) {
            return false;
        }
        // Add charge to booking
        roomBooking.getRoomCharges().add(roomCharge);
        // Update total price
        roomBooking.setTotalPrice(roomBooking.getTotalPrice() + roomCharge.getAmount());
        return true;
    }
}
