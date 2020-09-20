package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.Impl.ShipListPagingServiceImpl;
import com.space.service.ShipService;
import com.space.util.InternalUtils;
import com.space.controller.utils.ShipOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


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

//    @RequestMapping(value = "/rest/ships",method = RequestMethod.GET)
//    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
//                               Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
//                               Integer pageNumber, Integer pageSize, HttpServletResponse response){
//
//        List<Ship> returnList = shipService.findAll();
//
//        if(order==null)order= ShipOrder.ID;
//        if(pageNumber==null)pageNumber=0;
//        if(pageSize==null)pageSize=3;
//
//        if(name!=null){
//          returnList = returnList.stream().filter(x->x.getName().contains(name)).collect(Collectors.toList());
//        }
//        if(planet!=null){
//            returnList = returnList.stream().filter(x->x.getPlanet().contains(planet)).collect(Collectors.toList());
//        }
//        if(shipType!=null){
//            returnList = returnList.stream().filter(x->x.getShipType()==shipType).collect(Collectors.toList());
//        }
//        if(after!=null||before!=null) {
//            if (after == null) {
//
//                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()<=before).collect(Collectors.toList());
//            } else if (before == null) {
//
//                final long afterF = after;
//                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()>=afterF).collect(Collectors.toList());
//            } else {
//                final long afterF = after;
//                final long beforeF = before;
//                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()>=afterF && x.getProdDate().getTime()<=beforeF).collect(Collectors.toList());
//
//
//            }
//
//        }
//        if(isUsed!=null){
//            if(isUsed){
//                returnList = returnList.stream().filter(x->x.isUsed()).collect(Collectors.toList());
//            }else {
//                returnList = returnList.stream().filter(x->!x.isUsed()).collect(Collectors.toList());
//            }
//
//        }
//        if(minSpeed!=null){
//            returnList = returnList.stream().filter(x->x.getSpeed()>=minSpeed).collect(Collectors.toList());
//        }
//        if(maxSpeed!=null){
//            returnList = returnList.stream().filter(x->x.getSpeed()<=maxSpeed).collect(Collectors.toList());
//        }
//        if(minCrewSize!=null){
//            returnList = returnList.stream().filter(x->x.getCrewSize()>=minCrewSize).collect(Collectors.toList());
//        }
//        if(maxCrewSize!=null){
//            returnList = returnList.stream().filter(x->x.getCrewSize()<=maxCrewSize).collect(Collectors.toList());
//        }
//        if(minRating!=null){
//            //returnList = returnList.stream().filter(x->x.getRating()>=minRating).collect(Collectors.toList());
//            List<Ship> tmp = new ArrayList<>();
//            for(Ship sh:returnList){
//                BigDecimal rating = new BigDecimal(sh.getRating());
//                BigDecimal minR = new BigDecimal(minRating);
//                if(rating.compareTo(minR)>=0){
//                    tmp.add(sh);
//                }
//                returnList = tmp;
//            }
//
//        }
//        if(maxRating!=null){
//            List<Ship> tmp = new ArrayList<>();
//            for(Ship sh:returnList){
//                BigDecimal rating = new BigDecimal(sh.getRating());
//                BigDecimal maxR = new BigDecimal(maxRating);
//                if(rating.compareTo(maxR)<=0){
//                    tmp.add(sh);
//                }
//            returnList = tmp;
//            }
//        }
//
//
//        response.setStatus(HttpServletResponse.SC_OK);
//        return returnList;
//    }

    @RequestMapping(value = "/rest/ships",method = RequestMethod.GET)
    public List<Ship> getShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
                               Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                               Integer pageNumber, Integer pageSize, HttpServletResponse response){

        List<Ship> returnList = new ArrayList<>();

        if(order==null)order=ShipOrder.ID;
        if(pageNumber==null)pageNumber=0;
        pageNumber++;
        if(pageSize==null)pageSize=3;



                returnList = shipService.queryNamePlanet(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                minRating, maxRating);

        if(after!=null||before!=null){
            if(after!=null){

                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()>=after).collect(Collectors.toList());
            }
            if(before!=null){

                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()<=before).collect(Collectors.toList());
            }
        }

        final ShipOrder orderF = order;
        returnList.sort(new Comparator<Ship>() {
            int returnResult;
            @Override
            public int compare(Ship o1, Ship o2) {
                switch(orderF){
                    case ID:
                        if(o1.getId()>o2.getId()){
                            returnResult = 1;
                        }else if(o1.getId()<o2.getId()){
                            returnResult = -1;
                        }else {
                            returnResult = 0;
                        }
                        break;
                    case SPEED:
                        if(o1.getSpeed()>o2.getSpeed()){
                            returnResult = 1;
                        }else if(o1.getSpeed()<o2.getSpeed()){
                            returnResult = -1;
                        }else {
                            returnResult = 0;
                        }
                        break;
                    case DATE:
                        if(o1.getProdDate().getTime()>o2.getProdDate().getTime()){
                            returnResult = 1;
                        }else if(o1.getProdDate().getTime()<o2.getProdDate().getTime()){
                            returnResult = -1;
                        }else {
                            returnResult = 0;
                        }
                        break;
                    case RATING:
                        if(o1.getRating()>o2.getRating()){
                            returnResult = 1;
                        }else if(o1.getRating()<o2.getRating()){
                            returnResult = -1;
                        }else{
                            returnResult = 0;
                        }
                        break;

                }

                return returnResult;
            }
        });

        List<Ship> pageList = new ArrayList<>();
        int pageSumm = returnList.size()/pageSize; // количество страниц
        int summStr = returnList.size(); //количество записей
        int startStrToPage = pageSize * pageNumber - pageSize; // первая запись на странице
        int endStrToPage = pageSize * pageNumber; // последняя запись на странице
        if(returnList.size()%summStr>0)pageSumm++;
        if(startStrToPage>returnList.size())startStrToPage=0;
        if(endStrToPage>returnList.size())endStrToPage=returnList.size();
        for(int i = startStrToPage;i<endStrToPage;i++){
            pageList.add(returnList.get(i));
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return pageList;
    }








    @RequestMapping(value = "/rest/ships/count",method = RequestMethod.GET)
    public Integer getShipsCount(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
                                 Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                 Integer pageNumber, Integer pageSize, HttpServletResponse response){

        List<Ship> returnList = new ArrayList<>();

        if(order==null)order=ShipOrder.ID;
        if(pageNumber==null)pageNumber=0;
        pageNumber++;
        if(pageSize==null)pageSize=3;



        returnList = shipService.queryNamePlanet(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize,
                minRating, maxRating);

        if(after!=null||before!=null){
            if(after!=null){

                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()>=after).collect(Collectors.toList());
            }
            if(before!=null){

                returnList = returnList.stream().filter(x -> x.getProdDate().getTime()<=before).collect(Collectors.toList());
            }
        }

//        final ShipOrder orderF = order;
//        returnList.sort(new Comparator<Ship>() {
//            int returnResult;
//            @Override
//            public int compare(Ship o1, Ship o2) {
//                switch(orderF){
//                    case ID:
//                        if(o1.getId()>o2.getId()){
//                            returnResult = 1;
//                        }else if(o1.getId()<o2.getId()){
//                            returnResult = -1;
//                        }else {
//                            returnResult = 0;
//                        }
//                        break;
//                    case SPEED:
//                        if(o1.getSpeed()>o2.getSpeed()){
//                            returnResult = 1;
//                        }else if(o1.getSpeed()<o2.getSpeed()){
//                            returnResult = -1;
//                        }else {
//                            returnResult = 0;
//                        }
//                        break;
//                    case DATE:
//                        if(o1.getProdDate().getTime()>o2.getProdDate().getTime()){
//                            returnResult = 1;
//                        }else if(o1.getProdDate().getTime()<o2.getProdDate().getTime()){
//                            returnResult = -1;
//                        }else {
//                            returnResult = 0;
//                        }
//                        break;
//                    case RATING:
//                        if(o1.getRating()>o2.getRating()){
//                            returnResult = 1;
//                        }else if(o1.getRating()<o2.getRating()){
//                            returnResult = -1;
//                        }else{
//                            returnResult = 0;
//                        }
//                        break;
//
//                }
//
//                return returnResult;
//            }
//        });

//        List<Ship> pageList = new ArrayList<>();
//        int pageSumm = returnList.size()/pageSize; // количество страниц
//        int summStr = returnList.size(); //количество записей
//        int startStrToPage = pageSize * pageNumber - pageSize; // первая запись на странице
//        int endStrToPage = pageSize * pageNumber; // последняя запись на странице
//        if(returnList.size()%summStr>0)pageSumm++;
//        if(startStrToPage>returnList.size())startStrToPage=0;
//        if(endStrToPage>returnList.size())endStrToPage=returnList.size();
//        for(int i = startStrToPage;i<endStrToPage;i++){
//            pageList.add(returnList.get(i));
//        }

        response.setStatus(HttpServletResponse.SC_OK);
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

        if(!internalUtils.isInteger(id)||LID==0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        Ship Iship = shipService.findShipById(LID);
        if(Iship==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        if(ship.getName()!=null){
            if(ship.getName().length()>50||ship.getName().equals("")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
            Iship.setName(ship.getName());
        }

        if(ship.getPlanet()!=null){
            if(ship.getPlanet().length()>50||ship.getPlanet().equals("")){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
