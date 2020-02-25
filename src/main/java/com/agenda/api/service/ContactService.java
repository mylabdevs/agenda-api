package com.agenda.api.service;

import com.agenda.api.entity.Contact;
import com.agenda.api.service.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    ContactDTO save(ContactDTO contactDTO);

    Optional<ContactDTO> findByPhone(String phone);

    Optional<ContactDTO> findById(Long id);

    List<ContactDTO> findAll();

    void deleteById(Long id);

    Page<ContactDTO> findByContatsByParam(String param, Pageable pageable);

}
