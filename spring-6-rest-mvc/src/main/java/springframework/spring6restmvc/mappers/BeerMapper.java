package springframework.spring6restmvc.mappers;

import org.mapstruct.Mapper;
import springframework.spring6restmvc.entities.Beer;
import springframework.spring6restmvc.model.BeerDTO;

@Mapper(componentModel = "spring") // MapStruct
// @Mapper without configuration if you have the pom.xml plugin with the setting for default argumentComponent = "spring".
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
