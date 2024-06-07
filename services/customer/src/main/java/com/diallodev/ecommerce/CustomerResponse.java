package com.diallodev.ecommerce;

public record CustomerResponse(
        String id,

        String firstName,

        String lastName,

        String email,

        Address address
) {
}
