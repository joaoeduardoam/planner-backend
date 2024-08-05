package com.joaoeduardo.planner_backend.activity;

import java.time.LocalDateTime;

public record ActivityRequestPayload(String title, LocalDateTime occurs_at){
}
