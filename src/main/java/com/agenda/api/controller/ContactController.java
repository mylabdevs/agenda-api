package com.agenda.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.api.controller.dto.ContactDTO;
import com.agenda.api.controller.response.Response;
import com.agenda.api.controller.response.ResponseList;
import com.agenda.api.entity.Contact;
import com.agenda.api.service.ContactService;

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
	
	@GetMapping("contacts")
	public ResponseEntity<ResponseList<ContactDTO>> findAll(){
		
		ResponseList<ContactDTO> responses = new ResponseList<ContactDTO>();
		
		List<Contact> contacts = service.findAll(); 
		
		if (contacts.isEmpty() || contacts == null) {
			responses.addErros("Nenhum contato foi encontrado");
			return ResponseEntity.status(HttpStatus.OK).body(responses);
		}
		
		contacts.forEach(c -> responses.addData(c.toDTO()));
		
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
