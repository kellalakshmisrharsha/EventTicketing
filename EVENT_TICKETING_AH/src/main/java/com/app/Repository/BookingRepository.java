package com.app.Repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.*;

public interface BookingRepository extends JpaRepository<Booking,Long>{
	public List<Booking> findByUser(User user);
}
