package com.example.demo.service.impl;

import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repo;

    public TicketServiceImpl(TicketRepository repo) {
        this.repo = repo;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        return repo.save(ticket);
    }

    @Override
    public Ticket getTicket(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public List<Ticket> getAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
