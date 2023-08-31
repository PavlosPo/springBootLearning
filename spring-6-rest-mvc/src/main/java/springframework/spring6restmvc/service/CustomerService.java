package springframework.spring6restmvc.service;

import springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateById(UUID id, CustomerDTO customerDTO);

    boolean deleteById(UUID id);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer);
}
