package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.DeleteRentalUseCase;
import rental.model.rental.RentalId;

@RestController
@RequestMapping("/rental")
public class DeleteRentalByIdController {

    @Autowired
    private DeleteRentalUseCase deleteRentalUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteRentalUseCase.execute(RentalId.of(id));
        return ResponseEntity.noContent().build();
    }
}