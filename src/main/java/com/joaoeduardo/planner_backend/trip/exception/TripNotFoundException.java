package com.joaoeduardo.planner_backend.trip.exception;

public class TripNotFoundException extends RuntimeException{

    public TripNotFoundException(String message){

        super(message);

    }
}
