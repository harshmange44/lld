package org.hrsh.movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.List;

public class Show {
    private String id;
    private Movie movie;
    private Theatre theatre;
    private LocalDateTime showStartTime;
    private LocalDateTime endStartTime;
    private List<Seat> seats;
}
