package springframework.spring6restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import springframework.spring6restmvc.model.Customer;
import springframework.spring6restmvc.service.CustomerService;
import springframework.spring6restmvc.service.CustomerServiceImpl;

import javax.print.attribute.standard.Media;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)   // tries to create beer controller if not say that .class
class CustomerControllerTest {

    @Autowired  // Test Component
    MockMvc mockMvc;

    // Mockito
    @MockBean
    CustomerService customerService;  // env data

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();    // real data

    @Test
    void getListCustomers() throws Exception{

        // Mockito
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getCustomerById() throws Exception{
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        // Mockito
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        // Mock Mvc
        mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName").value(testCustomer.getCustomerName()));
    }
}