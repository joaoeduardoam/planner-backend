package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

//    @Autowired
    private final ParticipantService participantService;

//    @Autowired
    private final TripRepository tripRepository;


    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload tripRequestPayload){

        Trip newTrip = new Trip(tripRequestPayload);

        tripRepository.save(newTrip);

        participantService.registerParticipantsToEvent(tripRequestPayload.emails_to_invite(),newTrip.getId());

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));

    }

}
