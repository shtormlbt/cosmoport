package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipListPagingRepo extends PagingAndSortingRepository<Ship, Long> {

    List<Ship> findShipsByNameContainsAndPlanetContains(String name, String planet, Pageable pageable);


}
