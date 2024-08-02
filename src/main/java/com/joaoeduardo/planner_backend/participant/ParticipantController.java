package com.joaoeduardo.planner_backend.participant;

import com.joaoeduardo.planner_backend.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantRepository participantRepository;


    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload participantRequestPayload){
        Optional<Participant> participant = participantRepository.findById(id);

        if(participant.isPresent()){

            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(participantRequestPayload.name());

            participantRepository.save(rawParticipant);
//            participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(rawParticipant);
        }

        return ResponseEntity.notFound().build();

    }

}
