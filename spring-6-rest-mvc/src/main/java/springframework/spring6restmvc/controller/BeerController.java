package springframework.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springframework.spring6restmvc.model.Beer;
import springframework.spring6restmvc.service.BeerService;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor // creates the constructors
@RestController
@RequestMapping("/api/v1/beer") // default path mapping for every method inside here
public class BeerController {
    private final BeerService beerService;


    @DeleteMapping("/{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID id) {

        beerService.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{beerId}")
    public ResponseEntity updateById(
            @PathVariable("beerId") UUID beerId,
            @RequestBody Beer beer) {

        beerService.updateBeerById(beerId, beer);


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer) {  // it binds the request as a instance

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();    // will return the location of the newly created item
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());    // this will be added in the response entity

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET) // GET method
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)    // GET method
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {    // to get the id from the URL

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId);
    }
}
