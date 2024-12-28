package rental.infrastructure.repository.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rental.model.car.Car;

@Repository
public interface JpaCarRepository extends JpaRepository<CarEntity, Long> {
}
