package com.agenda.api.controller;

import com.agenda.api.controller.response.Response;
import com.agenda.api.service.ContactService;
import com.agenda.api.service.UserService;
import com.agenda.api.service.dto.ContactDTO;
import com.agenda.api.service.dto.UserDTO;
import com.agenda.api.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.agenda.api.util.Util.getAuthenticatedUserId;

@RestController
@RequestMapping("contact")
public class ContactController {

    private final ContactService service;

    private final UserService userService;

    public ContactController(ContactService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Response<ContactDTO>> create(@Valid @RequestBody ContactDTO dto, BindingResult result) {

        if (getAuthenticatedUserId() == null) {
            result.addError(new ObjectError("Usuario", "Usuario não autenticado"));
        }

        Response<ContactDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.addErros(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        dto.setUserid(getAuthenticatedUserId());
        dto = service.save(dto);

        response.setData(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Response<ContactDTO>> update(@Valid @RequestBody ContactDTO dto, BindingResult result) {

        Response<ContactDTO> response = new Response<>();

        Optional<ContactDTO> dtoOpt = service.findById(dto.getId());

        if (!dtoOpt.isPresent()) {
            result.addError(new ObjectError("Contato", "Contato não encontrado"));
        }

        if (!dtoOpt.get().getUserid().equals(Util.getAuthenticatedUserId())) {
            result.addError(new ObjectError("Contato", "Não autorizado a alterar o contato"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.addErros(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        dto.setUserid(dtoOpt.get().getUserid());
        dto = service.save(dto);

        response.setData(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Response<Page<ContactDTO>>> findAllByParam(
            @RequestParam(value = "search", defaultValue = "") String param,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") int linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "sortBy", defaultValue = "ASC") String sortBy) {

        Response<Page<ContactDTO>> responses = new Response<Page<ContactDTO>>();

        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(sortBy), orderBy);
        Page<ContactDTO> contactDTO = service.findByContatsByParam(param, pageable);

        if (contactDTO.isEmpty() || contactDTO.getContent() == null) {
            responses.addErros("Nenhum contato foi encontrado");
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        }

		responses.setData(contactDTO);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Response<String> response = new Response<String>();

        Optional<ContactDTO> c = service.findById(id);

        if (!c.isPresent()) {
            response.addErros("Contato de id " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        service.deleteById(id);
        response.setData(String.format("O contato de id: %d foi excluído com sucesso", id));
        return ResponseEntity.ok().body(response);

    }
}
