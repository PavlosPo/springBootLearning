package springframework.spring6restmvc.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import springframework.spring6restmvc.entities.Beer;
import springframework.spring6restmvc.model.BeerDTO;
import springframework.spring6restmvc.service.BeerService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor // creates the constructors
@RestController
@RequestMapping // default path mapping for every method inside here
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    private final BeerService beerService;

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID id) {
        if (!beerService.deleteById(id)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId")
                                         @NotNull(message = "must not be null")
                                         @NotBlank(message = "must not be blank")
                                         UUID beerId,
                                              @Validated @RequestBody BeerDTO beerDTO) {


        if (beerService.updateBeerById(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = BEER_PATH)
    // @RequestMapping(method = RequestMethod.POST)

    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beerDTO) {  // it binds the request as an instance
        // @Validated will validate the data coming from the request. Constraints created in the Java BEAN -> DTO objects.

        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();    // will return the location of the newly created item
        headers.add("Location", "/api/v1/beer/" + savedBeerDTO.getId().toString());    // this will be added in the response entity

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH) // GET method
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }


    @GetMapping(BEER_PATH_ID)    // GET method
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {    // to get the id from the URL

        log.debug("Get Beer by Id - in controller");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }


}
