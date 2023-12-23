package org.habittracker.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> getByChatId(long chatId);
    }