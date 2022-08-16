package com.lxisoft.hungryzone.service.impl;

import com.lxisoft.hungryzone.domain.UserExtra;
import com.lxisoft.hungryzone.repository.UserExtraRepository;
import com.lxisoft.hungryzone.service.UserExtraService;
import com.lxisoft.hungryzone.service.dto.UserExtraDTO;
import com.lxisoft.hungryzone.service.mapper.UserExtraMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserExtra}.
 */
@Service
@Transactional
public class UserExtraServiceImpl implements UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraServiceImpl.class);

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    public UserExtraServiceImpl(UserExtraRepository userExtraRepository, UserExtraMapper userExtraMapper) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraMapper = userExtraMapper;
    }

    @Override
    public UserExtraDTO save(UserExtraDTO userExtraDTO) {
        log.debug("Request to save UserExtra : {}", userExtraDTO);
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        return userExtraMapper.toDto(userExtra);
    }

    @Override
    public UserExtraDTO update(UserExtraDTO userExtraDTO) {
        log.debug("Request to save UserExtra : {}", userExtraDTO);
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        return userExtraMapper.toDto(userExtra);
    }

    @Override
    public Optional<UserExtraDTO> partialUpdate(UserExtraDTO userExtraDTO) {
        log.debug("Request to partially update UserExtra : {}", userExtraDTO);

        return userExtraRepository
            .findById(userExtraDTO.getId())
            .map(existingUserExtra -> {
                userExtraMapper.partialUpdate(existingUserExtra, userExtraDTO);

                return existingUserExtra;
            })
            .map(userExtraRepository::save)
            .map(userExtraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserExtraDTO> findAll() {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll().stream().map(userExtraMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the userExtras where Cart is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtraDTO> findAllWhereCartIsNull() {
        log.debug("Request to get all userExtras where Cart is null");
        return StreamSupport
            .stream(userExtraRepository.findAll().spliterator(), false)
            .filter(userExtra -> userExtra.getCart() == null)
            .map(userExtraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserExtraDTO> findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        return userExtraRepository.findById(id).map(userExtraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.deleteById(id);
    }
}
