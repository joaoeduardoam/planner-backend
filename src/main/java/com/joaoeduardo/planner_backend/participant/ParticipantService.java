package com.joaoeduardo.planner_backend.participant;

import com.joaoeduardo.planner_backend.participant.exception.ParticipantNotFoundException;
import com.joaoeduardo.planner_backend.trip.Trip;
import com.joaoeduardo.planner_backend.trip.exception.TripNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){

        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        participantRepository.saveAll(participants);


    }

    public ParticipantResponse registerParticipantByTrip(String email, Trip trip){

        Participant newParticipant = new Participant(email,trip);

        participantRepository.save(newParticipant);

        System.out.println("ID Participant: "+newParticipant.getId());

        return new ParticipantResponse(newParticipant.getId());

    }


    public void triggerConfirmationEmailToParticipants(Trip trip){


    }

    public void triggerConfirmationEmailToParticipant(Trip trip, String email){

    }

    public List<ParticipantData> getAllParticipants(UUID tripId) {

        List<Participant> participants = participantRepository.findByTripId(tripId);

        List<ParticipantData> participantsData = participants.stream().map(ParticipantData::new).toList();

        return participantsData;

    }

    public ParticipantData confirmParticipant(String name, UUID participantId) {

        Optional<Participant> participant = participantRepository.findById(participantId);

        if(participant.isPresent()){

            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(name);

            participantRepository.save(rawParticipant);

            this.triggerConfirmationEmailToParticipant(rawParticipant.getTrip(), rawParticipant.getEmail());

            return new ParticipantData(rawParticipant);
        }

        throw new ParticipantNotFoundException("Participant Not Found with ID: "+participantId);

    }
}













