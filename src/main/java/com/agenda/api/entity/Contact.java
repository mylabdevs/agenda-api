package com.agenda.api.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.agenda.api.controller.dto.ContactDTO;

import lombok.Data;

@Entity
@Data
@Table(name = "contact")
public class Contact implements Serializable{
	
	private static final long serialVersionUID = 4447040082645947335L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String phone;
	
	private String email;

	public ContactDTO toDTO() {
		ContactDTO dto = new ContactDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setPhone(phone);
		dto.setEmail(email);
		return dto;
	}

}
