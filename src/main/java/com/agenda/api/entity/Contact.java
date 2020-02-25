package com.agenda.api.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.agenda.api.service.dto.ContactDTO;

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

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	private User user;

}
