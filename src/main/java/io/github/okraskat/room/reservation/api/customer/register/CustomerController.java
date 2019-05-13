package io.github.okraskat.room.reservation.api.customer.register;

import io.github.okraskat.room.reservation.api.customer.register.dto.RegisterCustomerRequest;
import io.github.okraskat.room.reservation.api.customer.register.dto.RegisteredCustomer;
import io.github.okraskat.room.reservation.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
class CustomerController {

    private final CustomerValidator customerValidator;
    private final CustomerService customerService;

    @Autowired
    CustomerController(CustomerValidator customerValidator, CustomerService customerService) {
        this.customerValidator = customerValidator;
        this.customerService = customerService;
    }

    @PostMapping
    RegisteredCustomer registerCustomer(@RequestBody RegisterCustomerRequest customer) {
        customerValidator.validateCustomer(customer);
        long customerId = customerService.registerCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail());
        return new RegisteredCustomer(customerId);
    }
}
