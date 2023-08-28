package springframework.spring6restmvc.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springframework.spring6restmvc.model.Beer;
import springframework.spring6restmvc.service.BeerService;
import springframework.spring6restmvc.service.BeerServiceImpl;


import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)   // test splice, limit it to the Beer Controller
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired  // By Spring Boot - Jackson
    ObjectMapper objectMapper;

    @MockBean   // adds the service but as mockito mock instance, it does the Autowire
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();    // will reset the data for the upcoming test
    }

    @Test
    void testCreateNewBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        /*
            Here, you're using given() to define the behavior of the beerService mock.
            You're saying that when the saveNewBeer() method is called with any Beer object,
            it should return the second beer from the list of beers obtained from beerServiceImpl.
         */
        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1)); // modifying the list

        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        /*
                            The .content(objectMapper.writeValueAsString(beer)) part of the code is used to set
                            the request body of the simulated HTTP POST request in your MockMvc test.
                         */
                        .content(objectMapper.writeValueAsString(beer)))    // Serializes the Java POJO to JSON

                .andExpect(status().isCreated())    // did have created?
                .andExpect(header().exists("Location"));    // is there a Location?
    }

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // we are not returning data
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))   // json value and the actual value
                .andExpect(jsonPath("$.beerName").value(testBeer.getBeerName()));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));  // length is 3

    }

    @Test
    void testUpdateBeer() throws Exception {    // we don't return anything in the service, so we check that the "action ran"
        Beer beer = new BeerServiceImpl().listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId()) // put operation
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)));   // HTTP body

        /*
            This will verify one interaction where that is the .updateBeerById()
            So it checks that in the PUT method, this service.updateBeerById() was called once.
         */
        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));   // new method - verifying the interaction
    }

    @Test
    void testDeleteBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID,  beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        /* We can also initializing as global variable:
         *
         * @Captor
         * ArgumentCaptor<UUID> uuidArgumentCaptor;
         *
         * Instead of:
         *
         * ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
         * */
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);  // instance to capture deleted item UUID
        verify(beerService).deleteById(uuidArgumentCaptor.capture());   // we capture the deleted argument UUID

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());  // we are making sure we delete the same item UUID
    }
}