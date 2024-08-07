package com.joaoeduardo.planner_backend.trip.validation;

import com.joaoeduardo.planner_backend.config.ValidationException;
import com.joaoeduardo.planner_backend.trip.TripRequestPayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StartAndEndDatesTripValidator implements TripValidator{
    @Override
    public void validate(TripRequestPayload tripRequestPayload) {

        LocalDateTime startsAt = LocalDateTime.parse(tripRequestPayload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endsAt = LocalDateTime.parse(tripRequestPayload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);

        if(endsAt.isBefore(startsAt)){
            throw new ValidationException("The end date of the trip must be after the start date!");
        }

    }
}
