package com.diallodev.ecommerce;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
      return ResponseEntity.ok(service.createCustomer(request));
    }

    @PostMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<Collection<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }


    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(@PathVariable("customer-id") String customerId) {
        service.delete(customerId);
        return ResponseEntity.accepted().build();
    }

}
