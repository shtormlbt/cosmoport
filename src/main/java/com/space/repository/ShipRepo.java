package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShipRepo extends JpaRepository<Ship, Long> {
    Ship findShipsById(Long id);
    List<Ship> findByNameContainingIgnoreCase(String name);
    List<Ship> findByPlanetContainingIgnoreCase(String planet);
    List<Ship> findByProdDateBetween(Date minDate, Date maxDate);
    List<Ship> findByCrewSizeBetween(Integer min, Integer max);
    List<Ship> findBySpeedBetween(Double min, Double max);
    List<Ship> findByRatingBetween(Double min, Double max);
    List<Ship> findByShipType(ShipType shipType);
    List<Ship> findByIsUsed(Boolean isUsed);
}
