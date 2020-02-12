package com.agenda.api.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.agenda.api.entity.Contact;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ContactRepositoryTest {

	private static final String PHONE = "88888-88888";
	private static final String EMAIL = "teste@teste.com";

	@Autowired
	ContactRepository repository;

	@Before
	public void setup() {
		Contact c = new Contact();
		c.setName("Teste");
		c.setPhone(PHONE);
		c.setEmail(EMAIL);

		repository.save(c);
	}

	@After
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void testSave() {
		Contact c = new Contact();
		c.setName("Jairo");
		c.setPhone("99999-9999");
		c.setEmail("jairo@teste.com");

		Contact response = repository.save(c);

		assertThat(response.getId(), is(c.getId()));
		assertNotNull(response);
	}
	
	@Test
	public void testFindByEmail() {
		Optional<Contact> response = repository.findByPhoneEquals(PHONE);
		
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getEmail(), EMAIL);
		
	}

}
