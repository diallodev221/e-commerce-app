package com.diallodev.ecommerce.order;

import com.diallodev.ecommerce.kafka.OrderConfirmation;
import com.diallodev.ecommerce.kafka.OrderProducer;
import com.diallodev.ecommerce.customer.CustomerClient;
import com.diallodev.ecommerce.exception.BusinessException;
import com.diallodev.ecommerce.orderline.OrderLineRequest;
import com.diallodev.ecommerce.orderline.OrderLineService;
import com.diallodev.ecommerce.product.ProductClient;
import com.diallodev.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        // check the customer --> open feign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with provided ID: " + request.customerId()));

        // purchase the products -> product-ms (RestTemplate)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        // persist the order
        var order = repository.save(mapper.toOrder(request));

        // persist the order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo start payment process

        //  send the order confirmation --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                ));

        return order.getId();
    }


    public List<OrderResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No Order found with this ID " + orderId));
    }
}
