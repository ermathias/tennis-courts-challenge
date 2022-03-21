package com.tenniscourts.guests;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long>{
    
    public List<Guest> findByNameIgnoreCaseContaining(String name);
    
}
