package com.diallodev.ecommerce.kafka;

import com.diallodev.ecommerce.customer.CustomerResponse;
import com.diallodev.ecommerce.order.PaymentMethod;
import com.diallodev.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
