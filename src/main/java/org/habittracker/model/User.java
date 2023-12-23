package org.habittracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;


@Getter
@Entity(name = "usersDataTable")
public class User {
    @Id
    private Long chatId;
    private String userName;
    private String habitName;
    private String userState;

    public void setChatId(Long chatId) {this.chatId = chatId;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }
    public void setUserState(String userState) { this.userState = userState; }


    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", userName='" + userName + '\'' +
                ", habitName='" + habitName + '\'' +
                ", userState=" + userState + '\'' +
                '}';
    }
}
