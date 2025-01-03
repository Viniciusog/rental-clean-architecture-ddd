package rental.infrastructure.repository.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JpaRentalRepository extends JpaRepository<RentalEntity, Long> {
    @Query("SELECT r from Rental r WHERE r.customerId = :customerId")
    List<RentalEntity> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT r FROM Rental r WHERE r.carId = :carId AND r.startTime >= :startTime AND r.endTime <= :endTime")
    List<RentalEntity> findByCarIdAndDateRange(
            @Param("carId") Long carId,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime);
}