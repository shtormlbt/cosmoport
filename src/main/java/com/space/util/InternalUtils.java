package com.space.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

@Component
public class InternalUtils {
    private static final Logger logger = LoggerFactory.getLogger(InternalUtils.class);

    //округление до 2х знаков после запятой
    public double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    //расчет рейтинга
    public Double shipRating(Double speed, Boolean isUsed, Date prodDate){
        Double k = isUsed?0.5:1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        Integer prodYear = calendar.get(Calendar.YEAR);

        Integer prodDateYear = prodYear;
        Integer diffYear = 3019 - prodDateYear;
        Double ch = 80*speed*k;
        Double d = diffYear+1.0;
        Double rating = ch/d;
        return round(rating);
    }

    public static boolean isInteger(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }

        return true;
    }
}
