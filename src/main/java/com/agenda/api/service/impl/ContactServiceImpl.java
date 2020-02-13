package com.agenda.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agenda.api.entity.Contact;
import com.agenda.api.repository.ContactRepository;
import com.agenda.api.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository repository;

	@Override
	public Contact save(Contact contact) {
		return repository.save(contact);
	}

	@Override
	public Optional<Contact> findByPhone(String phone) {
		return repository.findByPhoneEquals(phone);
	}

	@Override
	public Optional<Contact> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Contact> findAll() {
		return repository.findAll().isEmpty() ? new ArrayList<Contact>() : repository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Page<Contact> findByContatsByParam(String param, Pageable pageable) {
//		String name = param.toLowerCase();
		return repository.findByParam(param, pageable);
	}

}
