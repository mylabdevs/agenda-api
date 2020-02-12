package com.agenda.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.api.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByPhoneEquals(String phone);

}
