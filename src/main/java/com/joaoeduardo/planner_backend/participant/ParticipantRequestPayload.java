package com.joaoeduardo.planner_backend.participant;

public record ParticipantRequestPayload(
        String name,
        String email
) {
    public ParticipantRequestPayload(Participant participant) {

        this(participant.getName(), participant.getEmail());
    }
}
