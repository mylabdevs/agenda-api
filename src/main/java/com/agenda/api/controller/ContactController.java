package com.agenda.api.controller;

import com.agenda.api.controller.dto.ContactDTO;
import com.agenda.api.controller.response.Response;
import com.agenda.api.entity.Contact;
import com.agenda.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("contact")
public class ContactController {

    @Autowired
    ContactService service;

    @PostMapping
    public ResponseEntity<Response<ContactDTO>> create(@Valid @RequestBody ContactDTO dto, BindingResult result) {

        Response<ContactDTO> response = new Response<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.addErros(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Contact contact = service.save(dto.toContact());

        response.setData(contact.toDTO());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @PutMapping
    public ResponseEntity<Response<ContactDTO>> update(@Valid @RequestBody ContactDTO dto, BindingResult result) {

        Response<ContactDTO> response = new Response<>();

        Optional<Contact> c = service.findById(dto.getId());

        if (!c.isPresent()) {
            result.addError(new ObjectError("Contato", "Contato não encontrado"));
        }

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.addErros(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Contact contact = service.save(dto.toContact());

        response.setData(contact.toDTO());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<Response<Page<ContactDTO>>> findAllBtParam(
            @RequestParam(value = "search", defaultValue = "") String param,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") int linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "sortBy", defaultValue = "ASC") String sortBy) {

        Response<Page<ContactDTO>> responses = new Response<Page<ContactDTO>>();

        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(sortBy), orderBy);

        Page<Contact> contacts = service.findByContatsByParam(param, pageable);

        if (contacts.isEmpty() || contacts.getContent() == null) {
            responses.addErros("Nenhum contato foi encontrado");
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        }

		Page<ContactDTO> contactDTOS = contacts.map(i -> i.toDTO());
		responses.setData(contactDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Response<String> response = new Response<String>();

        Optional<Contact> c = service.findById(id);

        if (!c.isPresent()) {
            response.addErros("Contato de id " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        service.deleteById(id);
        response.setData("O contato foi excluído com sucesso");
        return ResponseEntity.ok().body(response);

    }
}
