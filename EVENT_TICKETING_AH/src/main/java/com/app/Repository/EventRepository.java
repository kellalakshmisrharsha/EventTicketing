package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.*;

public interface EventRepository extends JpaRepository<Event,Long>{
	
}
