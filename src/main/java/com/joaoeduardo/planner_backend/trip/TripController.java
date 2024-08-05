package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.activities.ActivityData;
import com.joaoeduardo.planner_backend.activities.ActivityRequestPayload;
import com.joaoeduardo.planner_backend.activities.ActivityResponse;
import com.joaoeduardo.planner_backend.activities.ActivityService;
import com.joaoeduardo.planner_backend.participant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {


    private final ParticipantService participantService;

    private final ActivityService activityService;

    private final TripRepository tripRepository;


    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload tripRequestPayload){

        Trip newTrip = new Trip(tripRequestPayload);

        tripRepository.save(newTrip);

        participantService.registerParticipantsToEvent(tripRequestPayload.emails_to_invite(),newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));

    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID tripId){
        Optional<Trip> trip = tripRepository.findById(tripId);

        return trip.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID tripId,@RequestBody TripRequestPayload tripRequestPayload){

        Optional<Trip> trip = tripRepository.findById(tripId);

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

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            tripRepository.save(rawTrip);
            participantService.triggerConfirmationEmailToParticipants(rawTrip);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }

    @PostMapping("/{tripId}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantRequestPayload participantRequestPayload){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            ParticipantCreateResponse newParticipantResponse= participantService.registerParticipantToEvent(participantRequestPayload.email(), rawTrip);

            if (rawTrip.getIsConfirmed()){
                participantService.triggerConfirmationEmailToParticipant(rawTrip, participantRequestPayload.email());
            }



            return ResponseEntity.ok(newParticipantResponse);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            List<ParticipantData> participants = participantService.getAllParticipants(rawTrip.getId());


            return ResponseEntity.ok(participants);
        }

        return ResponseEntity.notFound().build();

    }


    @PostMapping("/{tripId}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID tripId, @RequestBody ActivityRequestPayload activityRequestPayload){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            ActivityResponse activityResponse= activityService.registerActivity(activityRequestPayload, rawTrip);


            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            List<ActivityData> activities = activityService.getAllActivities(rawTrip.getId());


            return ResponseEntity.ok(activities);
        }

        return ResponseEntity.notFound().build();

    }


}
