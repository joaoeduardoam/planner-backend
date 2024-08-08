package com.joaoeduardo.planner_backend.activity.validation;

import com.joaoeduardo.planner_backend.activity.ActivityRequestPayload;
import com.joaoeduardo.planner_backend.trip.Trip;
import com.joaoeduardo.planner_backend.trip.TripRequestPayload;

public interface ActivityValidator {

    void validate(ActivityRequestPayload activityRequestPayload, Trip trip);

}
