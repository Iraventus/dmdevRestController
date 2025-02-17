package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.CustomerCreateEditDto;
import org.board_games_shop.dto.CustomerReadDto;
import org.board_games_shop.dto.UserFilter;
import org.board_games_shop.mapper.CustomerCreateEditMapper;
import org.board_games_shop.mapper.CustomerReadMapper;
import org.board_games_shop.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerReadMapper customerReadMapper;
    private final CustomerCreateEditMapper customerCreateEditMapper;

    public List<CustomerReadDto> findAll() {
        return customerRepository.findAll().stream()
                .map(customerReadMapper::map)
                .toList();
    }

    public List<CustomerReadDto> findAll(UserFilter filter) {
        return customerRepository.findAllByFilter(filter).stream()
                .map(customerReadMapper::map)
                .toList();
    }

    public Optional<CustomerReadDto> findById(Long id) {
        return customerRepository.findById(id)
                .map(customerReadMapper::map);
    }

    @Transactional
    public CustomerReadDto create(CustomerCreateEditDto customerDto) {
        return Optional.of(customerDto)
                .map(customerCreateEditMapper::map)
                .map(customerRepository::saveAndFlush)
                .map(customerReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CustomerReadDto> update(Long id, CustomerCreateEditDto customerDto) {
        return customerRepository.findById(id)
                .map(entity -> customerCreateEditMapper.map(customerDto, entity))
                .map(customerRepository::saveAndFlush)
                .map(customerReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return customerRepository.findById(id)
                .map(entity -> {
                    customerRepository.delete(entity);
                    customerRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}