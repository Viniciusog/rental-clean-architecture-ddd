package rental.infrastructure.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query("""
            SELECT c from Customer c
            WHERE (:nameStarting IS NULL OR c.name LIKE CONCAT(:nameStarting, '%'))
            AND (:nameContaining IS NULL OR c.name LIKE CONCAT('%', :nameContaining, '%'))
            """)
    List<CustomerEntity> findByFilter(@Param("nameStarting") String nameStarting,
                                      @Param("nameContaining") String nameContaining);
}
