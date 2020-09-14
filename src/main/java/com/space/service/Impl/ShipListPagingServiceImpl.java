package com.space.service.Impl;

import com.space.model.Ship;
import com.space.repository.ShipListPagingRepo;
import com.space.service.ShipListPagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipListPagingServiceImpl implements ShipListPagingService {

    private static final Logger logger = LoggerFactory.getLogger(ShipListPagingServiceImpl.class);

    @Autowired
    private ShipListPagingRepo shipListPagingRepo;

    @Override
    public List<Ship> findShipsByNameContainsAndPlanetContains(String name, String planet, Pageable pageable) {
        logger.debug("findShipsByNameContainsAndPlanetContains "+ name+" "+planet);
        return shipListPagingRepo.findShipsByNameContainsAndPlanetContains(name, planet, pageable);
    }

    public Page findAll(Pageable pageable){
        logger.debug("findAll pagination");
        return shipListPagingRepo.findAll(pageable);
    }

    public List<Ship> findAll(){
        logger.debug("findAll");
        List<Ship> resultL  = new ArrayList<>();
        resultL = (List<Ship>) shipListPagingRepo.findAll();
        System.out.println(resultL.size());
        return resultL;
    }
}
