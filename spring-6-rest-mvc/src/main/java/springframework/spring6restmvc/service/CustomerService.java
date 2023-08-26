package springframework.spring6restmvc.service;

import springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void updateById(UUID id, Customer customer);

    void deleteById(UUID id);
}
