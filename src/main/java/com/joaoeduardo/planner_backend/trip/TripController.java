package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.activity.ActivityData;
import com.joaoeduardo.planner_backend.activity.ActivityRequestPayload;
import com.joaoeduardo.planner_backend.activity.ActivityResponse;
import com.joaoeduardo.planner_backend.activity.DayActivities;
import com.joaoeduardo.planner_backend.link.LinkData;
import com.joaoeduardo.planner_backend.link.LinkRequestPayload;
import com.joaoeduardo.planner_backend.link.LinkResponse;
import com.joaoeduardo.planner_backend.participant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload tripRequestPayload){

        Trip trip = tripService.createTrip(tripRequestPayload);

        return ResponseEntity.ok(new TripCreateResponse(trip.getId()));

    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID tripId){

        Trip trip = tripService.getTripDetails(tripId);

        return ResponseEntity.ok(trip);

    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID tripId,@RequestBody TripRequestPayload tripRequestPayload){

        Trip trip = tripService.updateTrip(tripId, tripRequestPayload);

        return ResponseEntity.ok(trip);

    }

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId){

        Trip trip = tripService.confirmTrip(tripId);

        return ResponseEntity.ok(trip);

    }



    ///// PARTICIPANTS
    @PostMapping("/{tripId}/invite")
    public ResponseEntity<ParticipantResponse> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantRequestPayload participantRequestPayload){

            ParticipantResponse newParticipantResponse = tripService.registerParticipantByTrip(participantRequestPayload.email(), tripId);

            return ResponseEntity.ok(newParticipantResponse);

    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID tripId){

        List<ParticipantData> participantDataList = tripService.getAllParticipantsByTrip(tripId);

        return ResponseEntity.ok(participantDataList);

    }


    ///// ACTIVITIES
    @PostMapping("/{tripId}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID tripId, @RequestBody ActivityRequestPayload activityRequestPayload){

        ActivityResponse activityResponse= tripService.registerActivityByTrip(activityRequestPayload, tripId);

        return ResponseEntity.ok(activityResponse);

    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<List<DayActivities>> getAllActivities(@PathVariable UUID tripId){

        List<DayActivities> dayActivitiesList = tripService.getAllActivitiesByTrip(tripId);

        return ResponseEntity.ok(dayActivitiesList);

    }



    ///// LINKS
    @PostMapping("/{tripId}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID tripId, @RequestBody LinkRequestPayload linkRequestPayload){

        LinkResponse linkResponse= tripService.registerLinkByTrip(linkRequestPayload, tripId);

        return ResponseEntity.ok(linkResponse);

    }

    @GetMapping("/{tripId}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID tripId){

        List<LinkData> linkDataList = tripService.getAllLinksByTrip(tripId);

        return ResponseEntity.ok(linkDataList);

    }


}
