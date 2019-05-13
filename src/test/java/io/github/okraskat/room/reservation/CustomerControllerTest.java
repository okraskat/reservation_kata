package io.github.okraskat.room.reservation;

import io.github.okraskat.room.reservation.api.customer.register.dto.RegisterCustomerRequest;
import io.github.okraskat.room.reservation.api.customer.register.dto.RegisteredCustomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Configuration
public class CustomerControllerTest {

	private RestTemplate restTemplate = new RestTemplateBuilder().build();

	@LocalServerPort
	private int port;

	@Test
	public void shouldRegisterCustomer() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();

		// when
		RegisteredCustomer registeredCustomer = restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);

		// then
		assertThat(registeredCustomer).isNotNull();
		assertThat(registeredCustomer.getCustomerId()).isGreaterThanOrEqualTo(1);
	}

	@Test(expected = HttpClientErrorException.BadRequest.class)
	public void shouldThrowExceptionWhenFirstNameIsEmpty() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();
		request.setFirstName(null);

		// when
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
	}

	@Test(expected = HttpClientErrorException.BadRequest.class)
	public void shouldThrowExceptionWhenLastNameIsEmpty() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();
		request.setLastName(null);

		// when
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
	}

	@Test(expected = HttpClientErrorException.BadRequest.class)
	public void shouldThrowExceptionWhenEmailIsEmpty() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();
		request.setEmail(null);

		// when
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
	}

	@Test(expected = HttpClientErrorException.BadRequest.class)
	public void shouldThrowExceptionWhenEmailIsInvalid() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();
		request.setEmail("mail.com");

		// when
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
	}

	@Test(expected = HttpClientErrorException.Conflict.class)
	public void shouldThrowExceptionWhenEmailAlreadyExists() {
		// given
		RegisterCustomerRequest request = CustomerFactory.createNewCustomer();
		request.setEmail("mail@mail.com");

		// when
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
		restTemplate.postForObject(String.format("http://localhost:%d/customers", port),
				request, RegisteredCustomer.class);
	}
}
