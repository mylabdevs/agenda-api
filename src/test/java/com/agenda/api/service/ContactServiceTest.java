package com.agenda.api.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.agenda.api.entity.Contact;
import com.agenda.api.repository.ContactRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactServiceTest {
	
	@MockBean
	ContactRepository repository;
	
	@Autowired
	ContactService service;
	
	@Before
	public void setup() {
		BDDMockito.given(repository.findByPhoneEquals(Mockito.anyString()))
			.willReturn(Optional.of(new Contact()));
	}
	
	@Test
	public void testFindByPhone() {
		Optional<Contact> contact = service.findByPhone("99999-99999");
		
		assertTrue(contact.isPresent());
	}

}
