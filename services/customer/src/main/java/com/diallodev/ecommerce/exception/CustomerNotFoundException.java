package com.diallodev.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException{

    private final String msg;

    public CustomerNotFoundException(String message1) {
        this.msg = message1;
    }
}
