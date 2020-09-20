package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ShipSpecifications {

//    public static Specification<Car> withLicensePlate(String licensePlate) {
//        return (root, query, cb) -> licensePlate == null ? null : cb.equal(root.get("licensePlate"), licensePlate);
//    }
//
//    public static Specification<Car> withRating(String rating) {
//        return (root, query, cb) -> rating == null ? null : cb.equal(root.get("rating"), rating);
//    }
//
//    public static Specification<Car> withName(String name) {
//        return (root, query, cb) -> name == null ? null : cb.equal(root.get("name"), name);
//    }

    public static Specification<Ship> hasName(String name){
        //return (ship, cq, cb) -> name == null ? null : cb.equal(ship.get("name"),name);
        return (ship, cq, cb) -> name == null ? null : cb.like(ship.get("name"),"%"+ name +"%");
    }

    public static Specification<Ship> hasPlanet(String planet){
        return (ship, cq, cb) -> planet == null?null: cb.like(ship.get("planet"),"%"+ planet +"%");
    }

    public static Specification<Ship> withShipType(ShipType shipType){
        return (ship, cq, cb) -> shipType == null ? null: cb.equal(ship.get("shipType"),shipType);
    }

    public static Specification<Ship> withIsUsed(Boolean isUsed){
        return (ship, cq, cb) -> isUsed == null ? null: cb.equal(ship.get("isUsed"),isUsed);
    }

    public static Specification<Ship> withMinSpeed(Double minSpeed){
        return (ship, cq, cb) -> minSpeed == null ? null: cb.ge(ship.<Double>get("speed"),minSpeed);
    }

    public static Specification<Ship> withMaxSpeed(Double maxSpeed){
        return (ship, cq, cb) -> maxSpeed == null ? null: cb.le(ship.<Double>get("speed"),maxSpeed);
    }

    public static Specification<Ship> withMinCrewSize(Integer minCrewSize){
        return (ship, cq, cb) -> minCrewSize == null ? null: cb.ge(ship.<Integer>get("crewSize"),minCrewSize);
    }

    public static Specification<Ship> withMaxCrewSize(Integer maxCrewSize){
        return (ship, cq, cb) -> maxCrewSize == null ? null: cb.le(ship.<Integer>get("crewSize"),maxCrewSize);
    }

    public static Specification<Ship> withMinRating(Double minRating){
        return (ship, cq, cb) -> minRating == null ? null: cb.ge(ship.<Double>get("rating"),minRating);
    }

    public static Specification<Ship> withMaxRating(Double maxRating){
        return (ship, cq, cb) -> maxRating == null ? null: cb.le(ship.<Double>get("rating"),maxRating);
    }
//    //Long after, Long before
//    public static Specification<Ship> withProdDateAfter(Date after){
//        return (ship, cq, cb) -> after == null ? null: cb.ge(ship.<Date>get("prodDate"),after);
//    }
//
//    public static Specification<Ship> withProdDateBefore(Long before){
//        return (ship, cq, cb) -> before == null ? null: cb.le(ship.<Long>get("prodDate"),before);
//    }
}
