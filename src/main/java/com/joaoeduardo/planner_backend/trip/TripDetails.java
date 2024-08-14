package com.joaoeduardo.planner_backend.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record TripDetails(

        UUID id,
        String destination,
        LocalDateTime starts_at,
        LocalDateTime ends_at,
        boolean is_confirmed

) {
    public TripDetails(Trip trip){
        this(trip.getId(), trip.getDestination(),
                LocalDateTime.parse(trip.getStartsAt().toString(), DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.parse(trip.getEndsAt().toString(), DateTimeFormatter.ISO_DATE_TIME),
                trip.getIsConfirmed());


    }
}
