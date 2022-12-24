package com.delivery_service.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserRequest {
    @NotNull(message = "[id] cannot be empty")
    @Min(value = 1, message = "[id] cannot be less the 0")
    private Long id;
    private String name;
    private String ref;
    private String phone;
    private String street;
    private String houseNumber;
}
