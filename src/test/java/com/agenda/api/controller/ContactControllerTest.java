package com.agenda.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agenda.api.controller.dto.ContactDTO;
import com.agenda.api.entity.Contact;
import com.agenda.api.service.ContactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ContactControllerTest {
	
	private static final String NAME = "Contato 1";

	private static final String PHONE = "9999999-9999";

	private static final String EMAIL = "teste@teste.com";

	private static final String URL = "/contact";

	private static final Long ID = 1L;

	@MockBean
	ContactService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Contact.class))).willReturn(getMockContact());
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayload(ID, NAME, PHONE, EMAIL))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.name").value(NAME))
		.andExpect(jsonPath("$.data.phone").value(PHONE));
	}
	
	
	@Test
	public void testSaveInvalidContact() throws JsonProcessingException, Exception {
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayload(ID, NAME, PHONE, "email"))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors[0]").value("Email inválido"));
	}
	
	public Contact getMockContact() {
		Contact c = new Contact();
		c.setId(ID);
		c.setName(NAME);
		c.setPhone(PHONE);
		c.setEmail(EMAIL);
		return c;
	}
	
	public String getJsonPayload(Long id, String name, String phone, String email) throws JsonProcessingException {
		ContactDTO dto = new ContactDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPhone(phone);
		dto.setEmail(email);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

}
