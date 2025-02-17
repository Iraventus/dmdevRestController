package org.board_games_shop.service;

import lombok.RequiredArgsConstructor;
import org.board_games_shop.dto.AccessoriesCreateEditDto;
import org.board_games_shop.dto.AccessoriesReadDto;
import org.board_games_shop.mapper.AccessoriesCreateEditMapper;
import org.board_games_shop.mapper.AccessoriesReadMapper;
import org.board_games_shop.repository.AccessoriesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessoriesService {

    private final AccessoriesCreateEditMapper accessoriesCreateEditMapper;
    private final AccessoriesReadMapper accessoriesReadMapper;
    private final AccessoriesRepository accessoriesRepository;

    public List<AccessoriesReadDto> findAll() {
        return accessoriesRepository.findAll().stream()
                .map(accessoriesReadMapper::map)
                .toList();
    }

    public Optional<AccessoriesReadDto> findById(Long id) {
        return accessoriesRepository.findById(id)
                .map(accessoriesReadMapper::map);
    }

    @Transactional
    public AccessoriesReadDto create(AccessoriesCreateEditDto accessoriesCreateEditDto) {
        return Optional.of(accessoriesCreateEditDto)
                .map(accessoriesCreateEditMapper::map)
                .map(accessoriesRepository::save)
                .map(accessoriesReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<AccessoriesReadDto> update(Long id, AccessoriesCreateEditDto accessoriesCreateEditDto) {
        return accessoriesRepository.findById(id)
                .map(entity -> accessoriesCreateEditMapper.map(accessoriesCreateEditDto, entity))
                .map(accessoriesRepository::saveAndFlush)
                .map(accessoriesReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return accessoriesRepository.findById(id)
                .map(entity -> {
                    accessoriesRepository.delete(entity);
                    accessoriesRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
