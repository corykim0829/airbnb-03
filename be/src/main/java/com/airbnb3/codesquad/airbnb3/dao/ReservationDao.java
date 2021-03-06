package com.airbnb3.codesquad.airbnb3.dao;

import com.airbnb3.codesquad.airbnb3.dto.ReservationsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final Logger logger = LoggerFactory.getLogger(ReservationDao.class);

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationsDto> findAllReservations() {
        String sql =
                "SELECT GROUP_CONCAT(i.image_url) AS image\n" +
                        "     , b.check_in_date\n" +
                        "     , b.check_out_date\n" +
                        "     , b.booking_date\n" +
                        "     , b.nights\n" +
                        "     , b.service_fee\n" +
                        "     , b.cleaning_fee\n" +
                        "     , b.tax\n" +
                        "     , p.price\n" +
                        "     , b.price_for_stay\n" +
                        "     , b.total_price\n" +
                        "     , p.number_of_reviews\n" +
                        "     , p.review_average\n" +
                        "     , b.guests\n" +
                        "     , p.id AS properties_id\n" +
                        "FROM bookings b\n" +
                        "         JOIN properties p ON b.properties_id = p.id\n" +
                        "         JOIN images i ON p.id = i.properties_id\n" +
                        "GROUP BY b.id;";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        ReservationsDto.builder()
                                       .images((rs.getString("image").split(",")[0]))
                                       .checkInDate(rs.getDate("check_in_date"))
                                       .checkOutDate(rs.getDate("check_out_date"))
                                       .bookingDate(rs.getDate("booking_date"))
                                       .nights(rs.getInt("nights"))
                                       .serviceFee(rs.getBigDecimal("service_fee"))
                                       .cleaningFee(rs.getBigDecimal("cleaning_fee"))
                                       .tax(rs.getBigDecimal("tax"))
                                       .price(rs.getBigDecimal("price"))
                                       .priceForStay(rs.getBigDecimal("price_for_stay"))
                                       .totalPrice(rs.getBigDecimal("total_price"))
                                       .numberOfReviews(rs.getInt("number_of_reviews"))
                                       .reviewAverage(rs.getDouble("review_average"))
                                       .guests(rs.getInt("guests"))
                                       .propertiesId(rs.getLong("properties_id"))
                                       .build()
        );
    }

    public ReservationsDto findByPropertyId(Long propertyId) {
        String sql =
                "SELECT GROUP_CONCAT(i.image_url) AS image\n" +
                        "     , b.check_in_date\n" +
                        "     , b.check_out_date\n" +
                        "     , b.booking_date\n" +
                        "     , b.nights\n" +
                        "     , b.service_fee\n" +
                        "     , b.cleaning_fee\n" +
                        "     , b.tax\n" +
                        "     , p.price\n" +
                        "     , b.price_for_stay\n" +
                        "     , b.total_price\n" +
                        "     , p.number_of_reviews\n" +
                        "     , p.review_average\n" +
                        "     , b.guests\n" +
                        "     , p.id AS properties_id\n" +
                        "FROM bookings b\n" +
                        "         JOIN properties p ON b.properties_id = p.id\n" +
                        "         JOIN images i ON p.id = i.properties_id\n" +
                        "WHERE p.id = ?\n" +
                        "GROUP BY b.id;";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                        ReservationsDto.builder()
                                       .images((rs.getString("image").split(",")[0]))
                                       .checkInDate(rs.getDate("check_in_date"))
                                       .checkOutDate(rs.getDate("check_out_date"))
                                       .bookingDate(rs.getDate("booking_date"))
                                       .nights(rs.getInt("nights"))
                                       .serviceFee(rs.getBigDecimal("service_fee"))
                                       .cleaningFee(rs.getBigDecimal("cleaning_fee"))
                                       .tax(rs.getBigDecimal("tax"))
                                       .price(rs.getBigDecimal("price"))
                                       .priceForStay(rs.getBigDecimal("price_for_stay"))
                                       .totalPrice(rs.getBigDecimal("total_price"))
                                       .numberOfReviews(rs.getInt("number_of_reviews"))
                                       .reviewAverage(rs.getDouble("review_average"))
                                       .guests(rs.getInt("guests"))
                                       .propertiesId(rs.getLong("properties_id"))
                                       .build()
                , propertyId);
    }

    public void insertReservationInformation(Long propertyId, Date checkIn, Date checkOut,
                                             Integer guests, Integer nights, String name) {
        String sql =
                "INSERT INTO bookings(check_in_date, check_out_date, booking_date, guests, cleaning_fee, service_fee, tax, price,\n" +
                        "                     price_for_stay, total_price, nights, properties_id)\n" +
                        "SELECT ?,\n" +
                        "       ?,\n" +
                        "       ?,\n" +
                        "       ?,\n" +
                        "       d.cleaning_fee,\n" +
                        "       d.service_fee,\n" +
                        "       d.tax,\n" +
                        "       p.price,\n" +
                        "       (p.price + d.cleaning_fee + d.service_fee + d.tax),\n" +
                        "       (p.price * ? + d.cleaning_fee + d.service_fee + d.tax),\n" +
                        "       ?,\n" +
                        "       p.id\n" +
                        "FROM properties p\n" +
                        "         JOIN detail d ON p.id = d.id " +
                        "WHERE p.id = ?";

        jdbcTemplate.update(sql, checkIn, checkOut, Timestamp.valueOf(LocalDateTime.now()), guests, nights, nights, propertyId);
    }

    public void deleteReservationInformation(Long propertyId) {

        String sql = "DELETE FROM bookings WHERE bookings.properties_id = ?";
        jdbcTemplate.update(sql, propertyId);
    }

    public void insertReservationDate(Long propertyId, Date checkIn, Integer nights) {

        for (int i = 0; i <= nights; i++) {
            String sql = "INSERT INTO calendar (reservation_date, properties_id)\n" +
                    "        VALUES (DATE_ADD( ?, INTERVAL ? DAY), ?);";
            jdbcTemplate.update(sql, checkIn, i, propertyId);
        }
    }

    public Boolean checkReservation(Date checkIn, Date checkOut) {

        String sql = "SELECT EXISTS(SELECT * FROM calendar WHERE reservation_date BETWEEN ? AND ?) AS is_booked;";
        return jdbcTemplate.queryForObject(
                sql, new Object[]{checkIn, checkOut},
                (rs, rowNum) -> rs.getBoolean("is_booked"));
    }

    public void deleteReservationDate(Long propertyId) {

        String sql = "DELETE FROM calendar WHERE properties_id = ?";
        jdbcTemplate.update(sql, propertyId);
    }

    public void updateReservableIsFalse(Long propertyId) {

        String sql = "UPDATE properties SET reservable = FALSE WHERE id = ?";
        jdbcTemplate.update(sql, propertyId);
    }

    public void updateReservableIsTrue(Long propertyId) {

        String sql = "UPDATE properties SET reservable = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, propertyId);
    }
}
