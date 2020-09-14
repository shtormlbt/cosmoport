package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipOrder;
import com.space.model.ShipType;
import com.space.service.Impl.ShipListPagingServiceImpl;
import com.space.service.ShipService;
import com.space.util.InternalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




@RestController
//@RequestMapping("/rest/ships")
public class RController {
    private static final Logger logger = LoggerFactory.getLogger(RController.class);
    @Autowired
    private ShipListPagingServiceImpl shipListPagingService;
    @Autowired
    private InternalUtils internalUtils;
    @Autowired
    private ShipService shipService;

    /**
     * get ships list
     * @return
     */

    @RequestMapping(value = "/rest/ships",method = RequestMethod.GET)
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
                               Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                               Integer pageNumber, Integer pageSize, HttpServletResponse response){
       List<Ship> returnList = new ArrayList<>();
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
//        Pageable pageable = new PageRequest(pageNumber,pageSize,sort);

       Boolean nameIsNull = true;
       Boolean planetIsNull = true;
       Boolean prodYearIsNull = true;
       Boolean crewSizeIsNull = true;
       Boolean maxSpeedIsNull = true;
       Boolean ratingIsNull = true;
       Boolean shipTypeIsNull = true;
       Boolean isUsedIsNull = true;


       if(name!=null&&!name.equals("")){
           nameIsNull = false;
           returnList.addAll(shipService.findByName(name));
       }
       if(planet!=null&&!planet.equals("")){
           planetIsNull = false;
           returnList.addAll(shipService.findByPlanet(planet));
       }
       if(after!=null||before!=null){
            if(after==null)after=0L;
            if(before==null)before=0L;
            prodYearIsNull = false;
            Date minDate = new Date();
            Date maxDate = new Date();
            minDate.setTime(after);
            maxDate.setTime(before);
            returnList.addAll(shipService.findByProdDate(minDate,maxDate));
       }

        if(maxCrewSize!=null||minCrewSize!=null){
           if(minCrewSize==null)minCrewSize=0;
           if(maxCrewSize==null)maxCrewSize=0;
            crewSizeIsNull = false;
            returnList.addAll(shipService.findByCrewSize(minCrewSize,maxCrewSize));
        }

        if(minSpeed!=null||maxSpeed!=null){
            if(minSpeed==null)minSpeed=0.0;
            if(maxSpeed==null)maxSpeed=0.0;
            maxSpeedIsNull = false;
            returnList.addAll(shipService.findBySpeed(minSpeed,maxSpeed));
        }

        if(minRating!=null||maxRating!=null){
            if(minRating==null)minRating=0.0;
            if(maxRating==null)maxRating=0.0;
            ratingIsNull = false;
            returnList.addAll(shipService.findByRating(minRating,maxRating));
        }

        if(shipType!=null){
            shipTypeIsNull = false;
            returnList.addAll(shipService.findByShipType(shipType));
        }

        if(isUsed!=null){
            isUsedIsNull = false;
            returnList.addAll(shipService.findByIsUsed(isUsed));
        }

        if(nameIsNull&&planetIsNull&&prodYearIsNull&&crewSizeIsNull&&maxSpeedIsNull&&ratingIsNull&&shipTypeIsNull&&isUsedIsNull){
            returnList = shipListPagingService.findAll();
        }



        //pageable.

        //returnList = shipListPagingService.findAll();
        logger.debug(returnList.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        return returnList;
    }

    @RequestMapping(value = "/rest/ships/count",method = RequestMethod.GET)
    public Integer getShipsCount(String name, String planet, ShipType shipType,Long after,Long before, Boolean isUsed, Double minSpeed,
                                    Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating){
        List<Ship> returnList = new ArrayList<>();
        returnList = shipListPagingService.findAll();
        logger.debug(returnList.toString());
        return returnList.size();
    }

    //создание корабля
    @RequestMapping(value = "/rest/ships",method = RequestMethod.POST)
    public Ship postShips(@RequestBody Ship ship, HttpServletResponse response){

        if(ship.getName()==null||ship.getName().length()>50||ship.getPlanet()==null||ship.getPlanet().length()>50||ship.getShipType()==null
    ||ship.getProdDate()==null||ship.getName().length()==0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        if(ship.isUsed()==null){
            ship.setUsed(false);
        }else{
            Boolean used = ship.isUsed();
            ship.setUsed(used);
        }

        if(ship.getSpeed()==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        ship.setSpeed(internalUtils.round(ship.getSpeed()));
        if(ship.getSpeed()<0.01||ship.getSpeed()>0.99){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if(ship.getCrewSize()<1||ship.getCrewSize()>9999){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        //дата выпуска в диапазоне 2800-3019
        if(ship.getProdDate()==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }else if(ship.getProdDate().getTime()<0L){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Calendar calendarMin = Calendar.getInstance();
        calendarMin.set(2800,0,1);
        Calendar calendarMax = Calendar.getInstance();
        calendarMax.set(3019,0,1);

        if(ship.getProdDate().before(calendarMin.getTime())||ship.getProdDate().after(calendarMax.getTime())){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        ship.setRating(internalUtils.shipRating(ship.getSpeed(),ship.isUsed(),ship.getProdDate()));

        shipService.save(ship);
        response.setStatus(HttpServletResponse.SC_OK);
        return ship;
    }

    @RequestMapping(value = "/rest/ships/{id}",method = RequestMethod.DELETE)
    public void getDeleteShip(@PathVariable String id, HttpServletResponse response){
        logger.info("Удаляю корабль с id {}",id);

        if(!internalUtils.isInteger(id)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long tId = Long.parseLong(id);


        if(tId%1!=0||tId==0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Ship Dship = shipService.findShipById(tId);
        if(Dship!=null){
            shipService.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @RequestMapping(value = "/rest/ships/{id}",method = RequestMethod.GET)
    public Ship findShipById(@PathVariable String id, HttpServletResponse response){
        if(!internalUtils.isInteger(id)||id.equals("0")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        Long tId = Long.parseLong(id);
        if(tId%1!=0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Ship ship = shipService.findShipById(tId);
        if(ship==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return ship;
    }

    /**
     *  String name, String planet, ShipType shipType,Long prodDAte, Boolean isUsed, Double speed, Integer crewSize
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/rest/ships/{id}",method = RequestMethod.POST)
    public Ship postShipById(@PathVariable String id, @RequestBody Ship ship, HttpServletResponse response){

        logger.info(" Вносим изменения в корабль id {}",id);

        Long LID = Long.parseLong(id);

        if(LID==null||LID%1L!=0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Ship Iship = shipService.findShipById(LID);
        if(Iship==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        if(ship.getName()!=null){
            if(ship.getName().length()>50){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            Iship.setName(ship.getName());
        }

        if(ship.getPlanet()!=null){
            if(ship.getPlanet().length()>50){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            Iship.setPlanet(ship.getPlanet());
        }

        if(ship.getShipType()!=null){
            Iship.setShipType(ship.getShipType());
        }

        if(ship.getProdDate()!=null){
            //дата выпуска в диапазоне 2800-3019
            if(ship.getProdDate().getTime()<0L){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }

            Calendar calendarMin = Calendar.getInstance();
            calendarMin.set(2800,0,1);
            Calendar calendarMax = Calendar.getInstance();
            calendarMax.set(3019,0,1);

            if(ship.getProdDate().before(calendarMin.getTime())||ship.getProdDate().after(calendarMax.getTime())){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }

            Iship.setProdDate(ship.getProdDate());
        }

        if(ship.isUsed()!=null){
            Iship.setUsed(ship.isUsed());
        }

        if(ship.getSpeed()!=null){
            Iship.setSpeed(internalUtils.round(ship.getSpeed()));
            if(Iship.getSpeed()<0.01||Iship.getSpeed()>0.99){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
        }

        if(ship.getCrewSize()!=null){
            if(ship.getCrewSize()<1||ship.getCrewSize()>9999){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
            Iship.setCrewSize(ship.getCrewSize());
        }

        Double rating = internalUtils.shipRating(Iship.getSpeed(),Iship.isUsed(),Iship.getProdDate());
        Iship.setRating(rating);
        shipService.save(Iship);
        response.setStatus(HttpServletResponse.SC_OK);
        return Iship;

    }
}
