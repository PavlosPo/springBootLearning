package springframework.spring6restmvc.service;

import org.springframework.stereotype.Service;
import springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BeerService {

    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID id);
}
