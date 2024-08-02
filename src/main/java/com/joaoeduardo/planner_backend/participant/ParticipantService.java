package com.joaoeduardo.planner_backend.participant;

import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){

        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        participantRepository.saveAll(participants);

        System.out.println(participants.get(0).getId());

    }


    public void triggerConfirmationEmailToParticipants(UUID tripId){


    }

}
