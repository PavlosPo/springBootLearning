package springframework.spring6restmvc.mappers;

import org.mapstruct.Mapper;
import springframework.spring6restmvc.entities.Customer;
import springframework.spring6restmvc.model.CustomerDTO;


/*
    Or use the @Mapper(componentModel = "spring") config, so the code does compile as @Component of Spring.
    If you want to miss it and just use @Mapper, you need to config it in the pom.xml plugin of the mapstruct the :
    <compilerArgs>
        <compilerArg>-Amapstruct.defaultComponentModel=spring</compilerArg>
    </compilerArgs>
 */
@Mapper // MapStruct
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
