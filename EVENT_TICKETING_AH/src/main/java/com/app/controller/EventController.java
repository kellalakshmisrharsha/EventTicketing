package com.app.controller;

import com.app.entity.Event;
import com.app.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.findEventById(id);
        if (event.isPresent()) {
            return new ResponseEntity<>(event.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        Optional<Event> event = eventService.findEventById(id);
        if (event.isPresent()) {
            eventService.deleteEvent(id);
            return new ResponseEntity<>("Event deleted successfully.", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        Optional<Event> eventOpt = eventService.findEventById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            // Update only the fields you want to allow updating
            event.setName(updatedEvent.getName());
            event.setDescription(updatedEvent.getDescription());
            event.setPricePerTicket(updatedEvent.getPricePerTicket());
            event.setTotalSeats(updatedEvent.getTotalSeats());
            event.setAvailableSeats(updatedEvent.getAvailableSeats());
            event.setVenue(updatedEvent.getVenue());
            event.setImageUrl(updatedEvent.getImageUrl());
            // ...add other fields as needed

            Event savedEvent = eventService.saveEvent(event);
            return new ResponseEntity<>(savedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Event not found.", HttpStatus.NOT_FOUND);
        }
    }
}