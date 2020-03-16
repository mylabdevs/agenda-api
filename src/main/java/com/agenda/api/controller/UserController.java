package com.agenda.api.controller;

import com.agenda.api.controller.response.Response;
import com.agenda.api.entity.dto.UsersDTO;
import com.agenda.api.service.UserService;
import com.agenda.api.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Response<Page<UsersDTO>>> findAll(@RequestParam(value = "search", defaultValue = "") String param,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "linesPerPage", defaultValue = "12") int linesPerPage,
                                                            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                            @RequestParam(value = "sortBy", defaultValue = "ASC") String sortBy) {
        Response<Page<UsersDTO>> responses = new Response<Page<UsersDTO>>();
        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(sortBy), orderBy);

        Page<UsersDTO> pages = service.findByUsers(pageable);

        responses.setData(pages);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }
}
