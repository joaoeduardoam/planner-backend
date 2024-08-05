package com.joaoeduardo.planner_backend.activities;

import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip){

        Activity newActivity = Activity.builder()
                .title(payload.title())
                .occursAt(payload.occurs_at())
                .trip(trip).build();

        activityRepository.save(newActivity);

        return new ActivityResponse(newActivity.getId());

    }
}
