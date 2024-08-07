package com.joaoeduardo.planner_backend.trip;

import com.joaoeduardo.planner_backend.activity.ActivityData;
import com.joaoeduardo.planner_backend.activity.ActivityRequestPayload;
import com.joaoeduardo.planner_backend.activity.ActivityResponse;
import com.joaoeduardo.planner_backend.activity.ActivityService;
import com.joaoeduardo.planner_backend.link.LinkData;
import com.joaoeduardo.planner_backend.link.LinkRequestPayload;
import com.joaoeduardo.planner_backend.link.LinkResponse;
import com.joaoeduardo.planner_backend.link.LinkService;
import com.joaoeduardo.planner_backend.participant.ParticipantData;
import com.joaoeduardo.planner_backend.participant.ParticipantResponse;
import com.joaoeduardo.planner_backend.participant.ParticipantService;
import com.joaoeduardo.planner_backend.trip.exception.TripNotFoundException;
import com.joaoeduardo.planner_backend.trip.validation.TripValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    private final ActivityService activityService;

    private final LinkService linkService;

    private final List<TripValidator> validators;





    public Trip createTrip(TripRequestPayload tripRequestPayload) {

        Trip newTrip = new Trip(tripRequestPayload);

        validators.forEach( v -> v.validate(tripRequestPayload));

        tripRepository.save(newTrip);

        participantService.registerParticipantsToEvent(tripRequestPayload.emails_to_invite(),newTrip);

        return newTrip;

    }

    public Trip getTripDetails(UUID tripId) {

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){
            return trip.get();
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }

    public Trip updateTrip(UUID tripId, TripRequestPayload tripRequestPayload) {

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();
            rawTrip.setDestination(tripRequestPayload.destination());
            rawTrip.setStartsAt(LocalDateTime.parse(tripRequestPayload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            rawTrip.setEndsAt(LocalDateTime.parse(tripRequestPayload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));

            tripRepository.save(rawTrip);

            return rawTrip;
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }

    public Trip confirmTrip(UUID tripId) {

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            tripRepository.save(rawTrip);
            participantService.triggerConfirmationEmailToParticipants(rawTrip);

            return rawTrip;
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }


    ///// PARTICIPANTS

    public ParticipantResponse registerParticipantByTrip(String email, UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            if (rawTrip.getIsConfirmed()){
                participantService.triggerConfirmationEmailToParticipant(rawTrip, email);
            }

            return participantService.registerParticipantByTrip(email, rawTrip);

        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }

    public List<ParticipantData> getAllParticipantsByTrip(UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            return participantService.getAllParticipants(rawTrip.getId());
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }


    ///// ACTIVITIES

    public ActivityResponse registerActivityByTrip(ActivityRequestPayload activityRequestPayload, UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            return activityService.registerActivity(activityRequestPayload, rawTrip);

        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }

    public List<ActivityData> getAllActivitiesByTrip(UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            return activityService.getAllActivities(rawTrip.getId());
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }



    //// LINKS
    public LinkResponse registerLinkByTrip(LinkRequestPayload linkRequestPayload, UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            return linkService.registerLink(linkRequestPayload, rawTrip);

        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }

    public List<LinkData> getAllLinksByTrip(UUID tripId){

        Optional<Trip> trip = tripRepository.findById(tripId);

        if(trip.isPresent()){

            Trip rawTrip = trip.get();

            return linkService.getAllLinks(rawTrip.getId());
        }

        throw new TripNotFoundException("Trip Not Found with ID: "+tripId);

    }



}
