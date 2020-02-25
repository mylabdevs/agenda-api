package com.agenda.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agenda.api.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	Optional<Contact> findByPhoneEquals(String phone);

	@Query("SELECT c from Contact c WHERE " +
			"(LOWER(c.name) LIKE lower(:param) " +
			"OR c.phone LIKE :param " +
			"OR LOWER(c.email) LIKE lower(:param) ) " +
			"and c.user.id = :id")
	Page<Contact> findByParam(@Param("param") String param, @Param("id") Long id, Pageable pageable);

}
