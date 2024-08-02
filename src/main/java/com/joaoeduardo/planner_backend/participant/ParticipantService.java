package com.joaoeduardo.planner_backend.participant;

import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){

        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        participantRepository.saveAll(participants);

        System.out.println(participants.get(0).getId());

    }

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip){

        Participant newParticipant = new Participant(email,trip);

        participantRepository.save(newParticipant);

        System.out.println("ID Participant: "+newParticipant.getId());

        return new ParticipantCreateResponse(newParticipant.getId());

    }


    public void triggerConfirmationEmailToParticipants(Trip trip){


    }

    public void triggerConfirmationEmailToParticipant(Trip trip, String email){

    }

}
