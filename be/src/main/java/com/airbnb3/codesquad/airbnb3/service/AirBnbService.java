package com.airbnb3.codesquad.airbnb3.service;

import com.airbnb3.codesquad.airbnb3.dao.DetailDaoAlex;
import com.airbnb3.codesquad.airbnb3.dao.PropertiesDaoAlex;
import com.airbnb3.codesquad.airbnb3.dto.DetailDtoAlex;
import com.airbnb3.codesquad.airbnb3.dto.PropertiesDtoAlex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.airbnb3.codesquad.airbnb3.common.CommonStaticsProperties.*;

@Service
public class AirBnbService {

    private static final Logger logger = LoggerFactory.getLogger(AirBnbService.class);

    @Autowired
    private PropertiesDaoAlex propertiesDao;

    @Autowired
    private DetailDaoAlex detailDao;

    public List<PropertiesDtoAlex> stayedProperties(String pageNumber, String adults, String children,
                                                    String checkIn, String checkOut, String minRange, String maxRange,
                                                    String minLatitude, String maxLatitude, String minLongitude, String maxLongitude) {
        Integer propertyRange = parseStringToPageNumInteger(pageNumber) * PAGE_VIEW_ITEM_COUNT;
        Integer accommodates = parseStringToAccommodatesInteger(adults, children);
        Map<String, Date> reservationDates = dateCompare(checkIn, checkOut);
        Date checkInDate = reservationDates.get("checkInDate");
        Date checkOutDate = reservationDates.get("checkOutDate");
        Double minPrice = parseStringToMinDouble(minRange);
        Double maxPrice = parseStringToMaxDouble(maxRange);
        Map<String, Double> location = parseStringToLocationDouble(minLatitude, maxLatitude, minLongitude, maxLongitude);
        logger.info("----------------- Main Query ---------------------");
        logger.info("propertyRange : {}", propertyRange);
        logger.info("accommodates : {}", accommodates);
        logger.info("-------------------- Date  -----------------------");
        logger.info("checkInDate : {}", checkInDate);
        logger.info("checkOutDate : {}", checkOutDate);
        logger.info("------------------- Price ------------------------");
        logger.info("minPrice : {}", minPrice);
        logger.info("maxPrice : {}", maxPrice);
        logger.info("------------------ Location ----------------------");
        logger.info("minLatitude : {}", location.get("minLatitude"));
        logger.info("maxLatitude : {}", location.get("maxLatitude"));
        logger.info("minLongitude : {}", location.get("minLongitude"));
        logger.info("maxLongitude : {}", location.get("maxLongitude"));
        logger.info("--------------------------------------------------");
        return propertiesDao.getStayedProperties(propertyRange, accommodates, checkInDate, checkOutDate, minPrice, maxPrice,location);
    }

    public DetailDtoAlex detailProperties(Long id) {
        return detailDao.getDetailProperties(id);
    }

    private Map<String, Date> dateCompare(String checkIn, String checkOut) {

        Map<String, Date> tempMap = new HashMap<>();
        Date checkInDate = parseStringToCheckInDate(checkIn);
        Date checkOutDate = parseStringToCheckOutDate(checkOut);

        if (checkInDate.compareTo(checkOutDate) > 0) {
            Date temp = checkInDate;
            checkInDate = checkOutDate;
            checkOutDate = temp;
        }
        tempMap.put("checkInDate", checkInDate);
        tempMap.put("checkOutDate", checkOutDate);
        return tempMap;
    }

    private Date parseStringToCheckInDate(String date) {
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            return Date.valueOf(LocalDate.now());
        }
    }

    private Date parseStringToCheckOutDate(String date) {
        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            return Date.valueOf(LocalDate.now().plusDays(1));
        }
    }

    private Double parseStringToMinDouble(String price) {
        try {
            return Double.parseDouble(price);
        } catch (IllegalArgumentException e) {
            return DEFAULT_MIN_PRICE;
        }
    }

    private Double parseStringToMaxDouble(String price) {
        try {
            return Double.parseDouble(price);
        } catch (IllegalArgumentException e) {
            return DEFAULT_MAX_PRICE;
        }
    }

    private Integer parseStringToPageNumInteger(String pageNum) {
        try {
            return Integer.parseInt(pageNum);
        } catch (IllegalArgumentException e) {
            return DEFAULT_PAGE_NUM;
        }
    }

    private Integer parseStringToAccommodatesInteger(String adults, String children) {
        Integer adult;
        Integer child;
        try {
            adult = Integer.parseInt(adults);
        } catch (IllegalArgumentException e) {
            adult = 1;
        }

        try {
            child = Integer.parseInt(children);
        } catch (IllegalArgumentException e) {
            child = 0;
        }
        return adult + child;
    }

    private Map<String, Double> parseStringToLocationDouble(String minLatitude, String maxLatitude, String minLongitude, String maxLongitude) {
        Map<String, Double> temp = new HashMap<>();
        try {
            temp.put("minLatitude", Double.parseDouble(minLatitude));
        } catch (IllegalArgumentException e) {
            temp.put("minLatitude", DEFAULT_MIN_LATITUDE);
        }

        try {
            temp.put("maxLatitude", Double.parseDouble(maxLatitude));
        } catch (IllegalArgumentException e) {
            temp.put("maxLatitude", DEFAULT_MAX_LATITUDE);
        }

        try {
            temp.put("minLongitude", Double.parseDouble(minLongitude));
        } catch (IllegalArgumentException e) {
            temp.put("minLongitude", DEFAULT_MIN_LONGITUDE);
        }

        try {
            temp.put("maxLongitude", Double.parseDouble(maxLongitude));
        } catch (IllegalArgumentException e) {
            temp.put("maxLongitude", DEFAULT_MAX_LONGITUDE);
        }
        return temp;
    }
}