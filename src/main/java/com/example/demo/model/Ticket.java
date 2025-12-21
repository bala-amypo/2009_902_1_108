package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketType;

    private Double basePrice;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
