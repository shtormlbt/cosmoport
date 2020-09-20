package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

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
    List<Ship> findAll();
    List<Ship> queryNamePlanet(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed,
                               Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating,
                               Double maxRating);
    Page<Ship> findAll(Integer pageNumber, Integer pageSize, Sort order);

}
