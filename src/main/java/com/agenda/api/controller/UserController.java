package com.agenda.api.controller;

import com.agenda.api.entity.RoleEnum;
import com.agenda.api.service.dto.UserDTO;
import com.agenda.api.controller.response.Response;
import com.agenda.api.entity.User;
import com.agenda.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO dto, BindingResult result) {

        Response<UserDTO> response = new Response<UserDTO>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addErros(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserDTO userDTO = service.save(dto);

        response.setData(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("all")
    public ResponseEntity<Response<List<UserDTO>>> findAll() {
        Response<List<UserDTO>> responses = new Response<List<UserDTO>>();

        List<UserDTO> userDTOS = service.findAll();
        responses.setData(userDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }
}
