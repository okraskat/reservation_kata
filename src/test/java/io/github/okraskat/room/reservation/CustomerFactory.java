package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.customer.register.dto.RegisterCustomerRequest;
import io.github.okraskat.room.reservation.api.customer.register.dto.RegisteredCustomer;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.client.RestTemplate;

class CustomerFactory {
    private final RestTemplate restTemplate;
    private final int port;

    CustomerFactory(RestTemplate restTemplate, int port) {
        this.restTemplate = restTemplate;
        this.port = port;
    }

    RegisteredCustomer registerCustomer() {
        RegisterCustomerRequest request = createNewCustomer();
        RegisteredCustomer registeredCustomer = restTemplate.postForObject(
                String.format("http://localhost:%d/customers", port),
                request,
                RegisteredCustomer.class);
        return registeredCustomer;
    }

    static RegisterCustomerRequest createNewCustomer() {
        RegisterCustomerRequest registerCustomerRequest = new RegisterCustomerRequest();
        registerCustomerRequest.setEmail(RandomStringUtils.randomAlphabetic(4) + "@mail.com");
        registerCustomerRequest.setLastName(RandomStringUtils.random(4));
        registerCustomerRequest.setFirstName(RandomStringUtils.random(4));
        return registerCustomerRequest;
    }
}
