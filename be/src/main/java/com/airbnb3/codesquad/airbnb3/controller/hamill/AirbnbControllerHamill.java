package com.airbnb3.codesquad.airbnb3.controller.hamill;

import com.airbnb3.codesquad.airbnb3.common.CommonMessage;
import com.airbnb3.codesquad.airbnb3.dto.hamill.BookingsDtoHamill;
import com.airbnb3.codesquad.airbnb3.dto.hamill.DetailDtoHamill;
import com.airbnb3.codesquad.airbnb3.dto.hamill.PropertiesDtoHamill;
import com.airbnb3.codesquad.airbnb3.service.hamill.AirbnbServiceHamill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static com.airbnb3.codesquad.airbnb3.common.CommonStaticsPropertiesHamill.*;

@RestController
@RequestMapping("/hamill")
public class AirbnbControllerHamill {
//
//    private static final Logger logger = LoggerFactory.getLogger(AirbnbControllerHamill.class);
//    private final AirbnbServiceHamill airbnbServiceHamill;
//
//    public AirbnbControllerHamill(AirbnbServiceHamill airbnbServiceHamill) {
//        this.airbnbServiceHamill = airbnbServiceHamill;
//    }
//
//    @GetMapping("/properties")
//    public ResponseEntity<List<PropertiesDtoHamill>> findAllProperties(
//            @RequestParam(value = "offset", required = false) String offset,
//            @RequestParam(value = "price_min", required = false, defaultValue = DEFAULT_MIN_PRICE) String priceMin,
//            @RequestParam(value = "price_max", required = false, defaultValue = DEFAULT_MAX_PRICE) String priceMax,
//            @RequestParam(value = "checkin", required = false) String checkIn,
//            @RequestParam(value = "checkout", required = false) String checkOut,
//            @RequestParam(value = "adults", required = false, defaultValue = DEFAULT_ADULTS_COUNT) String adults,
//            @RequestParam(value = "children", required = false, defaultValue = DEFAULT_CHILDREN_COUNT) String children) {
//
//        return new ResponseEntity<>(
//                airbnbServiceHamill.findAllProperties(offset, priceMin, priceMax, checkIn, checkOut, adults, children),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/properties/{propertiesId}")
//    public ResponseEntity<DetailDtoHamill> findByPropertiesId(@PathVariable Long propertiesId) {
//        return new ResponseEntity<>(airbnbServiceHamill.findByPropertiesId(propertiesId), HttpStatus.OK);
//    }
//
//    @GetMapping("/reservations")
//    public ResponseEntity<List<BookingsDtoHamill>> findAllReservations() {
//
//        return new ResponseEntity<>(airbnbServiceHamill.findAllReservations(), HttpStatus.OK);
//    }
//
//    @PutMapping("/reservations/{reservationId}")
//    public ResponseEntity<CommonMessage> reserveTheProperties(
//            @PathVariable Long reservationId,
//            @RequestParam(value = "checkin") Date checkIn,
//            @RequestParam(value = "checkout") Date checkOut,
//            @RequestParam(value = "guests") Integer guests,
//            @CookieValue(value = "name", required = false, defaultValue = "None") String name) {
//
//        airbnbServiceHamill.reserveTheProperties(reservationId, checkIn, checkOut, guests, name);
//        return new ResponseEntity<>(getMessage("200", "예약 성공"), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/reservations/{reservationId}")
//    public ResponseEntity<CommonMessage> cancelTheProperties(@PathVariable Long reservationId) {
//
//        airbnbServiceHamill.cancelTheProperties(reservationId);
//        return new ResponseEntity<>(getMessage("200", "예약 취소"), HttpStatus.OK);
//    }
//
//    private CommonMessage getMessage(String statusCode, String message) {
//
//        return CommonMessage.builder()
//                            .statusCode(statusCode)
//                            .message(message)
//                            .build();
//    }

}
