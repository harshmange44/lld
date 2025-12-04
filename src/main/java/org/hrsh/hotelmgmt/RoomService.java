package org.hrsh.hotelmgmt;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RoomService {
    private final Map<String, List<LocalDate[]>> roomBookingsMap = new ConcurrentHashMap<>(); // roomId -> [checkIn, checkOut] pairs

    public void addRoom(Hotel hotel, Room room) {
        if (hotel == null || room == null) {
            throw new IllegalArgumentException("Hotel and Room cannot be null");
        }
        hotel.getRoomList().add(room);
        roomBookingsMap.put(room.getRoomId(), new CopyOnWriteArrayList<>());
    }

    public List<Room> searchAvailableRooms(Hotel hotel, LocalDate checkIn, LocalDate checkOut, RoomType roomType) {
        if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Invalid dates");
        }

        return hotel.getRoomList().stream()
                .filter(room -> roomType == null || room.getRoomType() == roomType)
                .filter(room -> isRoomAvailable(room, checkIn, checkOut))
                .collect(Collectors.toList());
    }

    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        if (room == null || checkIn == null || checkOut == null) {
            return false;
        }

        // Check if room status is available
        if (!room.getRoomStatus().equals(RoomStatus.AVAILABLE)) {
            return false;
        }

        // Check for date conflicts
        List<LocalDate[]> bookings = roomBookingsMap.get(room.getRoomId());
        if (bookings == null) {
            return true;
        }

        return bookings.stream().noneMatch(booking -> {
            LocalDate bookedCheckIn = booking[0];
            LocalDate bookedCheckOut = booking[1];
            // Check for overlap
            return !(checkOut.isBefore(bookedCheckIn) || checkIn.isAfter(bookedCheckOut));
        });
    }

    public void reserveRoom(Room room, LocalDate checkIn, LocalDate checkOut) {
        if (room == null || checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        List<LocalDate[]> bookings = roomBookingsMap.computeIfAbsent(
                room.getRoomId(), k -> new CopyOnWriteArrayList<>());
        bookings.add(new LocalDate[]{checkIn, checkOut});
        room.setRoomStatus(RoomStatus.RESERVED);
    }

    public void releaseRoom(Room room, LocalDate checkIn, LocalDate checkOut) {
        if (room == null) {
            return;
        }
        List<LocalDate[]> bookings = roomBookingsMap.get(room.getRoomId());
        if (bookings != null) {
            bookings.removeIf(booking -> 
                    booking[0].equals(checkIn) && booking[1].equals(checkOut));
            if (bookings.isEmpty()) {
                room.setRoomStatus(RoomStatus.AVAILABLE);
            }
        }
    }

    public void issueRoomKey(Room room, RoomBooking booking) {
        if (room == null || booking == null) {
            return;
        }
        // Create room key for each guest
        for (Guest guest : booking.getGuests()) {
            RoomKey key = new RoomKey(
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(), // barcode
                    new Date(),
                    true,
                    false
            );
            key.assignRoom(room);
            room.getRoomKeys().add(key);
            booking.getRoomKeys().add(key);
        }
    }

    public void collectRoomKeys(Room room, RoomBooking booking) {
        if (room == null || booking == null) {
            return;
        }
        // Deactivate all keys for this booking
        for (RoomKey key : booking.getRoomKeys()) {
            key.setActive(false);
        }
        // Remove keys from room
        room.getRoomKeys().removeAll(booking.getRoomKeys());
    }

    public void scheduleHouseKeeping(Room room) {
        if (room == null) {
            return;
        }
        HouseKeeping houseKeeping = new HouseKeeping(
                "Room needs cleaning after checkout",
                new Date(),
                60, // 1 hour
                null // Will be assigned when housekeeper accepts
        );
        room.getHouseKeepingLogs().add(houseKeeping);
    }
}

