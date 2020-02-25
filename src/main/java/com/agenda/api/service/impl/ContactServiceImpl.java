package com.agenda.api.service.impl;

import com.agenda.api.entity.Contact;
import com.agenda.api.repository.ContactRepository;
import com.agenda.api.service.ContactService;
import com.agenda.api.service.dto.ContactDTO;
import com.agenda.api.service.mapper.ContactMapper;
import com.agenda.api.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository repository;

    private final ContactMapper mapper;

    public ContactServiceImpl(ContactRepository repository, ContactMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ContactDTO save(ContactDTO contactDTO) {
        log.debug("Request to save Contact : {}", contactDTO);
        Contact contact = mapper.toEntity(contactDTO);
        contact = repository.save(contact);

        return mapper.toDto(contact);
    }

    @Override
    public Optional<ContactDTO> findByPhone(String phone) {
        log.debug("Request to Contact find per Phone  : {}", phone);
        return repository.findByPhoneEquals(phone)
                .map(mapper::toDto);
    }

    @Override
    public Optional<ContactDTO> findById(Long id) {
        log.debug("Request to Contact find per id  : {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public List<ContactDTO> findAll() {
        log.debug("Request to Contact find All");
        List<Contact> list = repository.findAll().isEmpty() ? new ArrayList<Contact>() : repository.findAll();
        return mapper.toDto(list);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request to Contact delete per ig  : {}", id);
        repository.deleteById(id);
    }

    @Override
    public Page<ContactDTO> findByContatsByParam(String param, Pageable pageable) {
        log.debug("Request to Contact find per param  : {}", param);
        param = (param.isEmpty() || param == null) ? "%%" : "%" + param + "%";

        return repository.findByParam(param, Util.getAuthenticatedUserId(), pageable).map(mapper::toDto);
    }

}
