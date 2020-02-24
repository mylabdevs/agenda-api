package com.agenda.api.service.mapper;

import com.agenda.api.entity.RoleEnum;
import com.agenda.api.entity.User;
import com.agenda.api.service.dto.UserDTO;
import com.agenda.api.util.Bcrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.agenda.api.util.Bcrypt.getHash;

@Service
public class UserMapper implements EntityMapper<UserDTO, User> {

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        } else {
            User user = new User();
            user.setId(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(getHash(dto.getPassword()));
            user.setRole(RoleEnum.valueOf(dto.getRole()));
            return user;
        }
    }

    @Override
    public UserDTO toDto(User user) {
        return new UserDTO(user);
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        return dtoList.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDto(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
