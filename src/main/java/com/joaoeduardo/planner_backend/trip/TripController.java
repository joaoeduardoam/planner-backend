package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        Optional<Trip> trip = tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

}
