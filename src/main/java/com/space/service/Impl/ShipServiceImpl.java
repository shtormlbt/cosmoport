package com.space.service.Impl;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepo;
import com.space.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {
    private static final Logger logger = LoggerFactory.getLogger(ShipServiceImpl.class);
    @Autowired
    private ShipRepo shipRepo;
    @Override
    public Ship save(Ship ship) {
        shipRepo.save(ship);
        logger.info("Сохраняю корабль в базу! "+ship.toString());
        return ship;
    }

    @Override
    public Ship delete(String id) {
        logger.info("Удаляю корабль "+id);
        Optional<Ship> delShips = shipRepo.findById(Long.parseLong(id));
        Ship delShip = delShips.get();
        shipRepo.delete(delShip);
        return delShip;
    }

    @Override
    public Ship findShipById(Long id) {
        Ship ship = shipRepo.findShipsById(id);
        return ship;
    }

    @Override
    public List<Ship> findByName(String name) {
       return shipRepo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Ship> findByPlanet(String planet) {
        return shipRepo.findByPlanetContainingIgnoreCase(planet);
    }

    @Override
    public List<Ship> findByProdDate(Date minDate, Date maxDate) {
        return shipRepo.findByProdDateBetween(minDate,maxDate);
    }

    @Override
    public List<Ship> findByCrewSize(Integer min, Integer max) {
        return shipRepo.findByCrewSizeBetween(min,max);
    }

    @Override
    public List<Ship> findBySpeed(Double min, Double max) {
        return shipRepo.findBySpeedBetween(min,max);
    }

    @Override
    public List<Ship> findByRating(Double min, Double max) {
        return shipRepo.findByRatingBetween(min,max);
    }

    @Override
    public List<Ship> findByShipType(ShipType shipType) {
        return shipRepo.findByShipType(shipType);
    }

    @Override
    public List<Ship> findByIsUsed(Boolean isUsed) {
        return shipRepo.findByIsUsed(isUsed);
    }
}
