package com.app.dto;

public class BookingHistoryDto{
    private Long id;
    private String eventName;
    private int noOfTicketsBooked;
    private double totalAmount;
    private String status;
    // Add more fields if needed
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getNoOfTicketsBooked() {
		return noOfTicketsBooked;
	}
	public void setNoOfTicketsBooked(int noOfTicketsBooked) {
		this.noOfTicketsBooked = noOfTicketsBooked;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    // Getters and setters
}