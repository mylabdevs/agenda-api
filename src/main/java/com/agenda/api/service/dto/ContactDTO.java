package com.agenda.api.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import com.agenda.api.entity.Contact;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDTO {
	
	private Long id;
	
	@NotNull
	@Length(min=3, max = 100, message = "O nome deve conter entre 3 e 50 caracteres")
	private String name;
	
	@NotNull
	@Length(min=10, max = 100, message = "O telefone deve conter no minimo 9 caracteres")
	private String phone;
	
	@Email(message = "Email inválido")
	private String email;

	private Long userid;

	public ContactDTO() {
	}

	public ContactDTO(Contact c) {
		this.id = c.getId();
		this.name = c.getName();
		this.phone = c.getPhone();
		this.email = c.getEmail();
		this.userid = c.getUser().getId();
	}

}
