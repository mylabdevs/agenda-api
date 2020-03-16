package com.agenda.api.repository;

import com.agenda.api.entity.User;
import com.agenda.api.entity.dto.UsersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailEquals(String email);

    @Query("SELECT u.id as id, u.name as name, u.email as email, u.role as role, count(c.id) as contacts " +
            "FROM User u left join Contact c on u.id = c.user.id " +
            "group by u.id, c.user.id " +
            "ORDER BY u.name")
    Page<UsersDTO> findByUsersContact(Pageable pageable);
}
