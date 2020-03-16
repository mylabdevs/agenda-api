package com.agenda.api.service;

import com.agenda.api.entity.User;
import com.agenda.api.entity.dto.UsersDTO;
import com.agenda.api.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO save(@Valid UserDTO u);

    Optional<User> findByEmail(String email);

    List<UserDTO> findAll();

    Page<UsersDTO> findByUsers(Pageable pageable);
}
