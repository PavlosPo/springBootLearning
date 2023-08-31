package springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This use Project Lombok, it means that boilerplate such as getters, setters, equals and hashcode, toString method are all here.
 */
@Builder
@Data   // Lombok annotation
public class BeerDTO {

    private UUID id;

    private Integer version;

    @NotBlank
    @NotNull
    private String beerName;

    // @NotNull - We can not create one so we let it be null
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    private LocalDateTime createDate;
    private LocalDateTime updatedDate;
}
