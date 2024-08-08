package com.joaoeduardo.planner_backend.activity.validation;

import com.joaoeduardo.planner_backend.activity.ActivityRequestPayload;
import com.joaoeduardo.planner_backend.config.ValidationException;
import com.joaoeduardo.planner_backend.trip.Trip;
import com.joaoeduardo.planner_backend.trip.TripRequestPayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateActivityValidator implements ActivityValidator {
    @Override
    public void validate(ActivityRequestPayload activityRequestPayload, Trip trip) {

        LocalDateTime occursAt = LocalDateTime.parse(activityRequestPayload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME);

        if( !( occursAt.isAfter(trip.getStartsAt() ) && occursAt.isBefore(trip.getEndsAt()) ) ){
            throw new ValidationException("The activity date must be between the start date and end date of the trip!");
        }

    }
}
