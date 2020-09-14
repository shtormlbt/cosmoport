package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipListPagingService {
    List<Ship> findShipsByNameContainsAndPlanetContains(String name, String planet, Pageable pageable);
    List<Ship> findAll();
}
