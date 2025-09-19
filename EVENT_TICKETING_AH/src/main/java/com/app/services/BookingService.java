package com.app.services;

import com.app.entity.Booking;
import com.app.entity.Event;
import com.app.entity.User;
import com.app.Repository.BookingRepository;
import com.app.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    UserRepository ur;

    public List<Booking> findBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public Optional<Booking> findBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public Booking createBooking(User user, Event event, int numberOfTickets) {
        if (event.getAvailableSeats() >= numberOfTickets) {
            Booking booking = new Booking();
            booking.setEvent(event);
            booking.setUser(user);
            booking.setNoOfTicketsBooked(numberOfTickets);
            booking.setStatus("PENDING");
            event.setAvailableSeats(event.getAvailableSeats() - numberOfTickets);     
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Not enough seats available for this event.");
        }
    }
}