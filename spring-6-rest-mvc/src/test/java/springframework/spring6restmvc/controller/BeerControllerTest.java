package springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springframework.spring6restmvc.model.Beer;
import springframework.spring6restmvc.service.BeerService;
import springframework.spring6restmvc.service.BeerServiceImpl;


import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(BeerController.class)   // test splice, limit it to the Beer Controller
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean   // adds the service but as mockito mock instance, it does the Autowire
    BeerService beerService;

    BeerServiceImpl beerServiceImlp = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImlp.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // we are not returning data
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))   // json value and the actual value
                .andExpect(jsonPath("$.beerName").value(testBeer.getBeerName()));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImlp.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));  // length is 3

    }
}