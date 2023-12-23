package org.habittracker;

import org.habittracker.model.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class HabitBot {
    public static void main(String[] args) {
        SpringApplication.run(HabitBot.class, args);
    }
}
