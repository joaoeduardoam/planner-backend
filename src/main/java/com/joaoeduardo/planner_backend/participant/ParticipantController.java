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

    private final ParticipantService participantService;


    @PostMapping("/{participantId}/confirm")
    public ResponseEntity<ParticipantData> confirmParticipant(@PathVariable UUID participantId, @RequestBody ParticipantRequestPayload participantRequestPayload){

        ParticipantData participantData = participantService.confirmParticipant(participantRequestPayload.name(), participantId);

        return ResponseEntity.ok(participantData);

    }

}
