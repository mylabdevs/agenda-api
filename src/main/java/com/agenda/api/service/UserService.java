package com.agenda.api.service;

import com.agenda.api.service.dto.UserDTO;
import com.agenda.api.entity.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO save(@Valid UserDTO u);

    Optional<UserDTO> findByEmail(String email);

    List<UserDTO> findAll();
}
