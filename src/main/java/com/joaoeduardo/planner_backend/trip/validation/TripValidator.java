package com.joaoeduardo.planner_backend.trip.validation;

import com.joaoeduardo.planner_backend.trip.TripRequestPayload;

public interface TripValidator {

    void validate(TripRequestPayload tripRequestPayload);

}
