package com.ena.spring.finalex.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirlineTicket {

    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String seatClass;
    private String seatNumber;
    private double price; 

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;

        // Automatically determine price based on seat class
        if (seatClass == null) {
            this.price = 0.0;
        } else {
            switch (seatClass.trim().toLowerCase()) {
                case "economy":
                    this.price = 100.0;
                    break;
                case "first class":
                    this.price = 200.0;
                    break;
                default:
                    this.price = 150.0; // default price for unrecognized classes
            }
        }
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}