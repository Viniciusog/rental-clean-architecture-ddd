package rental.infrastructure.repository.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface JpaRentalRepository extends JpaRepository<RentalEntity, Long> {
    @Query("SELECT r from Rental r WHERE r.customerId = :customerId")
    List<RentalEntity> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT r FROM Rental r WHERE r.carId = :carId AND NOT (r.endTime < :startTime OR r.startTime > :endTime)")
    List<RentalEntity> findByCarIdAndDateRange(
            @Param("carId") Long carId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);

    //    @Query(value = "SELECT CASE WHEN COUNT(*) > 0
    //    THEN true ELSE false END FROM (SELECT 1 FROM Rental r WHERE r.car_id = :carId
    //    AND r.date_range = :dateRange LIMIT 1) AS temp", nativeQuery = true)
    @Query(value = "SELECT COUNT(*) > 0 FROM (SELECT 1 FROM Rental r " +
            "WHERE r.car_id = :carId AND NOT (r.end_time < :startTime OR r.start_time > :endTime) LIMIT 1) AS subquery",
            nativeQuery = true)
    boolean existsByCarIdAndTimeRange(@Param("carId") Long carId,
                                      @Param("startTime") Instant startTime,
                                      @Param("endTime") Instant endTime);
}