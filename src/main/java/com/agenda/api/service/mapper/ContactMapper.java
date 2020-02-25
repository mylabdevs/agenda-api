package com.agenda.api.service.mapper;

import com.agenda.api.entity.Contact;
import com.agenda.api.entity.User;
import com.agenda.api.service.dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ContactMapper implements EntityMapper<ContactDTO, Contact> {
    @Override
    public Contact toEntity(ContactDTO dto) {
        if (dto == null) {
            return null;
        } else {
            User user = new User();
            Contact contact = new Contact();
            contact.setId(dto.getId());
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            contact.setEmail(dto.getEmail());
            user.setId(dto.getUserid());
            contact.setUser(user);
            return contact;
        }
    }

    @Override
    public ContactDTO toDto(Contact contact) {
        return new ContactDTO(contact);
    }

    @Override
    public List<Contact> toEntity(List<ContactDTO> dtoList) {
        return dtoList.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDTO> toDto(List<Contact> contacts) {
        return contacts.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
