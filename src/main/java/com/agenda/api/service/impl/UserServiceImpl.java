package com.agenda.api.service.impl;

import com.agenda.api.entity.RoleEnum;
import com.agenda.api.entity.User;
import com.agenda.api.repository.UserRepository;
import com.agenda.api.service.UserService;
import com.agenda.api.service.dto.UserDTO;
import com.agenda.api.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDTO save(UserDTO dto) {
        log.debug("Request to save User : {}", dto);
        dto.setRole(RoleEnum.ROLE_USER.toString());
        User user = mapper.toEntity(dto);
        user = repository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        log.debug("Request to get User : {}", email);
        return repository.findByEmailEquals(email)
            .map(mapper::toDto);
    }

    @Override
    public List<UserDTO> findAll() {
        return mapper.toDto(repository.findAll());
    }
}
