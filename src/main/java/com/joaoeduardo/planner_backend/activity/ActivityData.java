package com.joaoeduardo.planner_backend.activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityData(UUID id, String title, LocalDateTime occurs_at){
    public ActivityData (Activity activity){
        this(activity.getId(), activity.getTitle(), activity.getOccursAt());
    }
}
