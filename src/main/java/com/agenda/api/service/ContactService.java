package com.agenda.api.service;

import java.util.List;
import java.util.Optional;

import com.agenda.api.entity.Contact;

public interface ContactService {
	
	Contact save(Contact contact);

	Optional<Contact> findByPhone(String phone);

	Optional<Contact> findById(Long id);

	List<Contact> findAll();

	void deleteById(Long id);

}
