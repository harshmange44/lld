package org.hrsh.movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MovieTicketBookingSystem {
    /*
      1. Multiple Shows
      2. Multiple Movies
      3. Multiple Theatres
      4. Book Tickets
      5. Reserve Seats before Booking for 15 minutes
     */

    private final List<Movie> movies;
    private final List<Show> shows;
    private final List<Theatre> theatres;
    private final Map<String, Booking> bookings;
    private final Map<String, Reservation> reservations;
    private final Map<String, String> seatToReservationMap; // seatId -> reservationId (improved structure)
    private final Map<User, List<Booking>> userBookingsIndex;
    private final NotificationService notificationService;

    public MovieTicketBookingSystem() {
        this.movies = new CopyOnWriteArrayList<>();
        this.shows = new CopyOnWriteArrayList<>();
        this.theatres = new CopyOnWriteArrayList<>();
        this.bookings = new ConcurrentHashMap<>();
        this.reservations = new ConcurrentHashMap<>();
        this.seatToReservationMap = new ConcurrentHashMap<>(); // Improved: seatId -> reservationId
        this.userBookingsIndex = new ConcurrentHashMap<>();
        this.notificationService = new NotificationService();
        
        // Start cleanup thread for expired reservations
        startReservationCleanup();
        
        // Start notification service
        notificationService.startNotificationService(reservations);
    }

    /**
     * Add a movie to the system
     */
    public void addMovie(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        movies.add(movie);
    }

    /**
     * Add a show to the system
     */
    public void addShow(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show cannot be null");
        }
        if (show.getSeats() == null || show.getSeats().isEmpty()) {
            throw new IllegalArgumentException("Show must have seats");
        }
        shows.add(show);
    }

    /**
     * Add a theatre to the system
     */
    public void addTheatre(Theatre theatre) {
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre cannot be null");
        }
        theatres.add(theatre);
    }

    /**
     * Search shows by movie, location, and date
     */
    public List<Show> searchShows(Movie movie, Location location, LocalDateTime date) {
        if (movie == null || location == null || date == null) {
            throw new IllegalArgumentException("Search parameters cannot be null");
        }
        
        return shows.stream()
                .filter(show -> show.getMovie().equals(movie))
                .filter(show -> show.getTheatre().getLocation().equals(location))
                .filter(show -> show.getShowStartTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    /**
     * Get available seats for a show
     */
    public List<Seat> getAvailableSeats(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("Show cannot be null");
        }
        
        return show.getSeats().stream()
                .filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    /**
     * Select and reserve seats for 15 minutes
     */
    public synchronized Reservation selectSeats(User user, Show show, List<Seat> seats) {
        if (user == null || show == null || seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        // Validate seats belong to the show
        if (!validateSeatsBelongToShow(show, seats)) {
            throw new IllegalArgumentException("Seats do not belong to this show");
        }

        // Check if seats are available
        if (!areSeatsAvailable(show, seats)) {
            throw new IllegalArgumentException("Seats are not available");
        }

        // Create reservation
        Reservation reservation = new Reservation(user, show, seats);
        reservations.put(reservation.getId(), reservation);
        
        // Mark seats as reserved
        markSeatsAsReserved(seats);
        
        // Track seat to reservation mapping
        for (Seat seat : seats) {
            seatToReservationMap.put(seat.getId(), reservation.getId());
        }

        // Send notification
        notificationService.sendNotification(user, NotificationType.OTHER,
                "Seats reserved. Reservation expires in " + Reservation.RESERVATION_TIMEOUT_MINUTES + " minutes.");

        return reservation;
    }

    /**
     * Get reservation by ID
     */
    public Reservation getReservation(String reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }
        return reservations.get(reservationId);
    }

    /**
     * Book tickets from a reservation
     */
    public synchronized Booking bookTickets(String reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }
        
        Reservation reservation = reservations.get(reservationId);
        
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        // Check if reservation expired
        if (reservation.isExpired()) {
            releaseReservation(reservation);
            throw new IllegalStateException("Reservation has expired");
        }

        // Validate seats are still reserved
        if (!areSeatsReserved(reservation.getSeats())) {
            releaseReservation(reservation);
            throw new IllegalStateException("Seats are no longer reserved");
        }

        // Mark seats as booked
        markSeatsAsBooked(reservation.getSeats());
        
        // Calculate total price
        double totalPrice = calcTotalPrice(reservation.getSeats());
        
        // Create booking
        Booking booking = new Booking(
            reservation.getUser(),
            reservation.getShow(),
            reservation.getSeats(),
            totalPrice,
            BookingStatus.PENDING
        );
        
        // Store booking
        bookings.put(booking.getId(), booking);
        
        // Update user bookings index
        userBookingsIndex.computeIfAbsent(reservation.getUser(), k -> new ArrayList<>()).add(booking);
        
        // Remove reservation and clean up tracking
        reservations.remove(reservationId);
        for (Seat seat : reservation.getSeats()) {
            seatToReservationMap.remove(seat.getId());
        }

        // Send notification
        notificationService.sendNotification(reservation.getUser(), NotificationType.BOOKING_CONFIRMED,
                "Booking created. Booking ID: " + booking.getId() + ". Total: $" + booking.getTotalPrice());

        return booking;
    }

    /**
     * Confirm booking (after payment)
     */
    public synchronized boolean confirmBooking(String bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        
        Booking booking = bookings.get(bookingId);
        
        if (booking == null) {
            return false;
        }

        if (booking.getBookingStatus() == BookingStatus.PENDING) {
            booking.setBookingStatus(BookingStatus.SUCCESSFUL);
            
            // Send notification
            notificationService.sendNotification(booking.getUser(), NotificationType.BOOKING_CONFIRMED,
                    "Booking confirmed successfully. Show: " + booking.getShow().getMovie().getTitle() 
                    + " at " + booking.getShow().getShowStartTime());
            
            return true;
        }
        return false;
    }

    /**
     * Cancel booking
     */
    public synchronized boolean cancelBooking(String bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        
        Booking booking = bookings.get(bookingId);
        
        if (booking == null) {
            return false;
        }

        if (booking.getBookingStatus() == BookingStatus.SUCCESSFUL) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            markSeatsAsAvailable(booking.getSelectedSeats());
            
            // Send notification
            notificationService.sendNotification(booking.getUser(), NotificationType.BOOKING_CANCELLED,
                    "Booking cancelled. Refund will be processed.");
            
            return true;
        }
        return false;
    }

    /**
     * Get booking by ID
     */
    public Booking getBooking(String bookingId) {
        if (bookingId == null) {
            return null;
        }
        return bookings.get(bookingId);
    }

    /**
     * Get user's bookings
     */
    public List<Booking> getUserBookings(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return new ArrayList<>(userBookingsIndex.getOrDefault(user, Collections.emptyList()));
    }

    // Private helper methods

    private synchronized void markSeatsAsReserved(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.RESERVED);
        }
    }

    private synchronized void markSeatsAsAvailable(List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            seat.setSeatStatus(SeatStatus.AVAILABLE);
        }
    }

    private double calcTotalPrice(List<Seat> seats) {
        return seats.stream().mapToDouble(Seat::getPrice).sum();
    }

    private synchronized boolean areSeatsAvailable(Show show, List<Seat> seats) {
        // Validate seats belong to show
        if (!validateSeatsBelongToShow(show, seats)) {
            return false;
        }

        // Check if seats are available
        for (Seat seat : seats) {
            if (seat == null || seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        return true;
    }

    private synchronized boolean areSeatsReserved(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat == null || seat.getSeatStatus() != SeatStatus.RESERVED) {
                return false;
            }
        }
        return true;
    }

    private synchronized void markSeatsAsBooked(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.OCCUPIED);
        }
    }

    private boolean validateSeatsBelongToShow(Show show, List<Seat> requestedSeats) {
        List<Seat> showSeats = show.getSeats();
        Set<String> showSeatIds = showSeats.stream()
                .map(Seat::getId)
                .collect(Collectors.toSet());

        return requestedSeats.stream()
                .allMatch(seat -> showSeatIds.contains(seat.getId()));
    }

    private synchronized void releaseReservation(Reservation reservation) {
        for (Seat seat : reservation.getSeats()) {
            // Only release if seat is still RESERVED (not already OCCUPIED)
            if (seat.getSeatStatus() == SeatStatus.RESERVED) {
                seat.setSeatStatus(SeatStatus.AVAILABLE);
            }
            seatToReservationMap.remove(seat.getId());
        }
        
        // Send notification about expiry
        notificationService.sendNotification(reservation.getUser(), NotificationType.RESERVATION_EXPIRY,
                "Your reservation has expired. Seats have been released.");
        
        reservations.remove(reservation.getId());
    }

    private void startReservationCleanup() {
        Thread cleanupThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Check every minute
                    cleanupExpiredReservations();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        cleanupThread.setDaemon(true);
        cleanupThread.start();
    }

    private synchronized void cleanupExpiredReservations() {
        List<String> expiredIds = reservations.values().stream()
                .filter(Reservation::isExpired)
                .map(Reservation::getId)
                .collect(Collectors.toList());

        for (String id : expiredIds) {
            Reservation reservation = reservations.get(id);
            if (reservation != null) {
                releaseReservation(reservation);
            }
        }
    }

    // Getters
    public List<Movie> getMovies() {
        return new ArrayList<>(movies);
    }

    public List<Show> getShows() {
        return new ArrayList<>(shows);
    }

    public List<Theatre> getTheatres() {
        return new ArrayList<>(theatres);
    }

    /**
     * Get notifications for a user
     */
    public List<Notification> getUserNotifications(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return notificationService.getNotifications(user.getId());
    }

    /**
     * Mark notification as read
     */
    public void markNotificationAsRead(User user, String notificationId) {
        if (user == null || notificationId == null) {
            throw new IllegalArgumentException("User and notification ID cannot be null");
        }
        notificationService.markAsRead(user.getId(), notificationId);
    }
}
