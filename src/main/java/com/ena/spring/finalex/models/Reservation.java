package com.ena.spring.finalex.models;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reservation {

    private String status;
    private LocalDateTime reservationDate;
    private AirlineTicket ticket;

    public String getStatus() {
        return status;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public AirlineTicket getTicket() {
        return ticket;
    }

    // === Setters ===
    public void setStatus(String status) {
        this.status = status;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setTicket(AirlineTicket ticket) {
        this.ticket = ticket;
    }
}
