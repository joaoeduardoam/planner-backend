package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.participant.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id,@RequestBody TripRequestPayload tripRequestPayload){

        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();
            rawTrip.setDestination(tripRequestPayload.destination());
            rawTrip.setStartsAt(LocalDateTime.parse(tripRequestPayload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setEndsAt(LocalDateTime.parse(tripRequestPayload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));

            tripRepository.save(rawTrip);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){

        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            tripRepository.save(rawTrip);
            participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }


}
