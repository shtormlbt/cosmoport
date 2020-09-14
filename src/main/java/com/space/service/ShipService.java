package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.Date;
import java.util.List;

public interface ShipService {
    Ship save(Ship ship);
    Ship delete(String id);
    Ship findShipById(Long id);
    List<Ship> findByName(String name);
    List<Ship> findByPlanet(String planet);
    List<Ship> findByProdDate(Date minDate, Date maxDate);
    List<Ship> findByCrewSize(Integer min,Integer max);
    List<Ship> findBySpeed(Double min,Double max);
    List<Ship> findByRating(Double min,Double max);
    List<Ship> findByShipType(ShipType shipType);
    List<Ship> findByIsUsed(Boolean isUsed);
}
