package org.habittracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import javax.swing.plaf.nimbus.State;
import java.sql.Timestamp;
import java.util.ArrayList;

@Entity(name = "usersDataTable")
public class User {
    @Id
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;

    private String habitName;


    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitName() {
        return habitName;
    }

    private Timestamp registeredAt;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt + '\'' +
                '}';
    }
}
