package org.Habittracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.ArrayList;

@Entity(name = "usersDataTable")
public class User {
    @Id
    private Long chatId;

    private String firstName;
    private String lastName;
    private String userName;
    private ArrayList<String> Habits = new ArrayList<String>();
    private String timeNotification;

    private Timestamp registeredAt;

    public void setTime(String time){
        this.timeNotification = time;
    }
    public String getTime(){return timeNotification;}
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
    public void setHabits(ArrayList<String> Habits){
        this.Habits = Habits;
    }
    public ArrayList<String> getHabits(){
        return Habits;
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt='" + registeredAt + '\'' +
                ", timeNotification=" + timeNotification +
                '}';
    }
}
