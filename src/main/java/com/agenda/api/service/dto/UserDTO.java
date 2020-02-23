package com.agenda.api.service.dto;

import com.agenda.api.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotNull

    @Length(min = 6, message = "A senha deve ter no minimo 6 caracteres")
    private String password;

    @Length(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres")
    private String name;

    @Email(message = "Email inválido")
    private String email;

//    @NotNull(message = "Informe uma role de acesso")
//    @Pattern(regexp="^(ROLE_ADMIN|ROLE_git commitUSER)$", message = "Para a role de acesso somente são aceitos os valores ROLE_ADMIN ou ROLE_USER")
    private String role;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
    }
}
