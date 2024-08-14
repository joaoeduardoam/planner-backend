package com.joaoeduardo.planner_backend.activity;

import com.joaoeduardo.planner_backend.trip.Trip;
import com.joaoeduardo.planner_backend.trip.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload activityRequestPayload, Trip trip){

        LocalDateTime occursAt = LocalDateTime.parse(activityRequestPayload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME);

        Activity newActivity = Activity.builder()
                .title(activityRequestPayload.title())
                .occursAt(occursAt)
                .trip(trip).build();


        activityRepository.save(newActivity);

        return new ActivityResponse(newActivity.getId());

    }

    public List<DayActivities> getAllActivities(UUID tripId) {

        List<Activity> activities = activityRepository.findByTripId(tripId);

        List<ActivityData> activitiesData = activities.stream().map(ActivityData::new).toList();

        List<DayActivities> dayActivitiesList = getActivitiesByDay(activitiesData);


        return dayActivitiesList;
    }

    private List<DayActivities> getActivitiesByDay(List<ActivityData> activitiesData) {


        Map<LocalDate, List<ActivityData>> groupedByDay = activitiesData.stream()
                .collect(Collectors.groupingBy(activity -> activity.occurs_at().toLocalDate()));

        Map<LocalDate, List<ActivityData>> sortedGroupedByDay = new TreeMap<>(groupedByDay);

        sortedGroupedByDay.forEach((key, value) ->
                value.sort(Comparator.comparing(ActivityData::occurs_at))
        );
//        sortedGroupedByDay.forEach((key, value) ->
//                value.sort((a, b) -> a.occurs_at().compareTo(b.occurs_at()))
//        );



        List<DayActivities> dayActivitiesList = sortedGroupedByDay.entrySet().stream()
                .map(entry -> new DayActivities(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


        return dayActivitiesList;

    }
}
