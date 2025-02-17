package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CustomerCreateEditDto;
import org.board_games_shop.dto.CustomerReadDto;
import org.board_games_shop.entity.Role;
import org.board_games_shop.nodeModel.AddressNode;
import org.board_games_shop.repository.BaseIT;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.board_games_shop.nodeModel.AddressNode.getAddressConvertedToJsonNode;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
public class CustomerServiceIT extends BaseIT {

    private static final Long USER_1 = 1L;
    private static final String COUNTRY = "someCountry";
    private static final int apartmentNumber = 12;
    private static final String CITY = "someCity";
    private static final int houseNumber = 3;
    private static final String STREET_NAME = "someStreetName";

    private final CustomerService customerService;

    @Test
    void findAll() {
        List<CustomerReadDto> result = customerService.findAll();
        assertThat(result).hasSize(4);
    }

    @Test
    void findById() {
        Optional<CustomerReadDto> maybeCustomer = customerService.findById(USER_1);
        assertTrue(maybeCustomer.isPresent());
        maybeCustomer.ifPresent(customer -> assertEquals("Nick@gmail.com", customer.getLogin()));
    }

    @Test
    void create() {
        CustomerCreateEditDto customerDto = new CustomerCreateEditDto(
                "test@gmail.com",
                "qwertyuiop",
                "UserTestName",
                "UserTestLastName",
                LocalDate.now(),
                "88005553535",
                Role.CUSTOMER,
                AddressNode.builder()
                        .country(COUNTRY)
                        .city(CITY)
                        .streetName(STREET_NAME)
                        .houseNumber(houseNumber)
                        .apartmentNumber(apartmentNumber)
                        .build()
        );
        CustomerReadDto actualResult = customerService.create(customerDto);

        assertEquals(customerDto.getLogin(), actualResult.getLogin());
        assertEquals(customerDto.getLastname(), actualResult.getLastname());
        assertEquals(customerDto.getPassword(), actualResult.getPassword());
        assertEquals(customerDto.getAddress(), actualResult.getAddress());
    }

    @Test
    void update() {
        CustomerCreateEditDto customerDto = new CustomerCreateEditDto(
                "test@gmail.com",
                "qwertyuiop",
                "UserTestName",
                "UserTestLastName",
                LocalDate.now(),
                "88005553535",
                Role.CUSTOMER,
                AddressNode.builder()
                        .country(COUNTRY)
                        .city(CITY)
                        .streetName(STREET_NAME)
                        .houseNumber(houseNumber)
                        .apartmentNumber(apartmentNumber)
                        .build()
        );

        Optional<CustomerReadDto> actualResult = customerService.update(USER_1, customerDto);
        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(
                customer -> {
                    assertEquals(customerDto.getLogin(), customer.getLogin());
                    assertEquals(customerDto.getLastname(), customer.getLastname());
                    assertEquals(customerDto.getPassword(), customer.getPassword());
                    assertEquals(customerDto.getAddress(), customer.getAddress());
                });
    }

    @Test
    void delete() {
        assertTrue(customerService.delete(USER_1));
        assertFalse(customerService.delete(-12L));
    }
}
