package com.joaoeduardo.planner_backend.activities;

import com.joaoeduardo.planner_backend.trip.Trip;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityRequestPayload(String title, LocalDateTime occurs_at){
}
