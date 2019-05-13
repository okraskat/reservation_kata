package io.github.okraskat.room.reservation.service;

import io.github.okraskat.room.reservation.exceptions.customer.CustomerEmailAlreadyExistsException;
import io.github.okraskat.room.reservation.repository.CustomerRepository;
import io.github.okraskat.room.reservation.repository.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public long registerCustomer(String firstName, String lastName, String email) {
        validateEmailIsAlreadyUsed(email);
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

    private void validateEmailIsAlreadyUsed(String email) {
        customerRepository.findByEmail(email)
            .ifPresent(c -> {
                throw new CustomerEmailAlreadyExistsException(email);
            });
    }
}
