package com.joaoeduardo.planner_backend.activities;

import com.joaoeduardo.planner_backend.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
}
