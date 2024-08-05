package com.joaoeduardo.planner_backend.activity;

import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<ActivityData> getAllActivities(UUID tripId) {
        List<Activity> activities = activityRepository.findByTripId(tripId);

        List<ActivityData> activitiesData = activities.stream().map(ActivityData::new).toList();

        return activitiesData;
    }
}
