package com.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.app.Repository.BookingRepository;
import com.app.Repository.EventRepository;
import com.app.Repository.UserRepository;
import com.app.config.RazorpayConfig;
import com.app.dto.BookingHistoryDto;
import com.app.dto.OrderRequest;
import com.app.dto.PaymentVerification;
import com.app.entity.Booking;
import com.app.entity.Event;
import com.app.entity.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;


@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    @Autowired
    private RazorpayConfig razorpayConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest){
        try {
            RazorpayClient razorpayClient = new RazorpayClient(razorpayConfig.getKeyId(), razorpayConfig.getKeySecret());
            JSONObject orderRequestJson = new JSONObject();
            orderRequestJson.put("amount", orderRequest.getAmount() * 100); 
            orderRequestJson.put("currency", orderRequest.getCurrency());
            orderRequestJson.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpayClient.orders.create(orderRequestJson);

            // Return as JSON, not string
            return new ResponseEntity<>(order.toJson().toMap(), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerification verification) {
        try {
            JSONObject options = new JSONObject(); 
            options.put("razorpay_order_id", verification.getRazorpayOrderId());
            options.put("razorpay_payment_id", verification.getRazorpayPaymentId());
            options.put("razorpay_signature", verification.getRazorpaySignature());

            boolean isValidSignature = Utils.verifyPaymentSignature(options, razorpayConfig.getKeySecret());

            if (isValidSignature) {
                Optional<User> userOpt = userRepository.findById(verification.getUserId());
                Optional<Event> eventOpt = eventRepository.findById(verification.getEventId());
                if (userOpt.isPresent() && eventOpt.isPresent()) {
                    Booking booking = new Booking();
                    booking.setUser(userOpt.get());
                    booking.setEvent(eventOpt.get());
                    booking.setNoOfTicketsBooked(verification.getNoOfTicketsBooked());
                    booking.setTotalAmount(verification.getTotalAmount());
                    booking.setRazorPayId(verification.getRazorpayPaymentId());
                    booking.setStatus("CONFIRMED");
                    bookingRepository.save(booking);
                    System.out.println("saved");
                    return new ResponseEntity<>("Payment verified and booking saved successfully.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("User or Event not found.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Payment verification failed.", HttpStatus.BAD_REQUEST);
            }
        } catch (RazorpayException e) {
            return new ResponseEntity<>("Payment verification failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getBookingHistory(@PathVariable Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<Booking> history = bookingRepository.findByUser(userOpt.get());
        List<BookingHistoryDto> dtos = history.stream().map(booking -> {
            BookingHistoryDto dto = new BookingHistoryDto();
            dto.setId(booking.getId());
            dto.setEventName(booking.getEvent() != null ? booking.getEvent().getName() : "N/A");
            dto.setNoOfTicketsBooked(booking.getNoOfTicketsBooked());
            dto.setTotalAmount(booking.getTotalAmount());
            dto.setStatus(booking.getStatus());
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}