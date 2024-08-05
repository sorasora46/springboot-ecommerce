package me.sora.eCommerce.dto.Order;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderRequest {

    @NotEmpty
    private String streetAddress;

    @NotEmpty
    private String city;

    @NotEmpty
    private String country;

    @NotEmpty
    private String postCode;

}
