package springframework.spring6restmvc.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springframework.spring6restmvc.entities.Beer;
import springframework.spring6restmvc.model.BeerDTO;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                            .beerName("My Beer")
                        .upc("12343343")
                        .price(new BigDecimal("12211"))
                        .build());

        beerRepository.flush(); // we tell it to flush here, instead of automatically doing it, because
        // it is so fast that passes all the validation needed in the Entities.
        // so writing it here is slower, but it creates the validation steps.


        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer012212212324241414124242424421412Beer012212212324241414124242424421412Beer012212212324241414124242424421412Beer012212212324241414124242424421412Beer012212212324241414124242424421412")
                    .upc("12343343")
                    .price(new BigDecimal("12.11"))
                    .build());

            beerRepository.flush();
        });
    }
}