package com.joaoeduardo.planner_backend.link;

import com.joaoeduardo.planner_backend.activity.Activity;

import java.time.LocalDateTime;
import java.util.UUID;

public record LinkData(UUID id, String title, String url){
    public LinkData(Link link){
        this(link.getId(), link.getTitle(), link.getUrl());
    }
}
