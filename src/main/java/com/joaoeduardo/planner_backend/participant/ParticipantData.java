package com.joaoeduardo.planner_backend.participant;

import java.util.UUID;

public record ParticipantData(
        UUID id,
        String name,
        String email,
        Boolean is_confirmed
) {
    public ParticipantData(Participant participant) {

        this(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed());
    }
}
