package com.joaoeduardo.planner_backend.activity;

import com.joaoeduardo.planner_backend.trip.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayActivities {


    private LocalDate date;

    private List<ActivityData> activities;


}
