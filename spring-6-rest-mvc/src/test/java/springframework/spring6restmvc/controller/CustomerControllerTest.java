package springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springframework.spring6restmvc.model.Customer;
import springframework.spring6restmvc.service.CustomerService;
import springframework.spring6restmvc.service.CustomerServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)   // tries to create beer controller if not say that .class
class CustomerControllerTest {

    @Autowired  // Test Component
    MockMvc mockMvc;

    // Mockito
    @MockBean
    CustomerService customerService;  // env data

    @Autowired  // Jackson with Spring Boot
    ObjectMapper objectMapper;

    CustomerServiceImpl customerServiceImpl;    // real data

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;    // this captures the arguments passed in the HTTP METHODS

    @BeforeEach
    void setUp() {  // Recreated
        customerServiceImpl = new CustomerServiceImpl();    // Initializing the Data each time
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0); // Initial new Customer
        customer.setVersion(null);
        customer.setId(null);

        /* If passed any Customer instance, will return the next one */
        given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getListCustomers() throws Exception{

        // Mockito
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getCustomerById() throws Exception{
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        // Mockito
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.of(testCustomer));

        // Mock Mvc
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName").value(testCustomer.getCustomerName()));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception{

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCustomerById() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        // verifies method of customerService MockBean was called
        // and captures what the UUID values was  with the uuidArgumentCaptor
        verify(customerService).updateById(uuidArgumentCaptor.capture(), any(Customer.class));

        // verify(customerService).updateById(any(UUID.class), any(Customer.class));

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }



    @Test
    void testDeleteCustomerById() throws Exception{
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        /* We can initializing as global variable:
        *
        * @Captor
        * ArgumentCaptor<UUID> uuidArgumentCaptor;
        *
        * */

        // ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);/
        verify(customerService).deleteById(uuidArgumentCaptor.capture());
        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }
}