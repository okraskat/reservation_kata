package io.github.okraskat.room.reservation.api.customer.register;

import io.github.okraskat.room.reservation.api.customer.register.dto.RegisterCustomerRequest;
import io.github.okraskat.room.reservation.exceptions.customer.CustomerEmptyFieldException;
import io.github.okraskat.room.reservation.exceptions.customer.CustomerInvalidEmailAddressException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
class CustomerValidator {

    void validateCustomer(RegisterCustomerRequest customer) {
        if (StringUtils.isEmpty(customer.getFirstName())) {
            throw new CustomerEmptyFieldException("first name");
        }
        if (StringUtils.isEmpty(customer.getLastName())) {
            throw new CustomerEmptyFieldException("last name");
        }
        String email = customer.getEmail();
        if (StringUtils.isEmpty(email)) {
            throw new CustomerEmptyFieldException("email");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new CustomerInvalidEmailAddressException(email);
        }
    }
}
