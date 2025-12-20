package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private boolean booked;

    private Double currentPrice;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
