package com.agenda.api.service;

import java.util.List;
import java.util.Optional;

import com.agenda.api.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
	
	Contact save(Contact contact);

	Optional<Contact> findByPhone(String phone);

	Optional<Contact> findById(Long id);

	List<Contact> findAll();

	void deleteById(Long id);

	Page<Contact> findByContatsByParam(String param, Pageable pageable);

}
