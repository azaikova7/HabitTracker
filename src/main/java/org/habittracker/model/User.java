package org.habittracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;


@Getter
@Entity(name = "usersDataTable")
public class User {
    @Id
    private Long userId;
    private String userName;
    private String habitName;
    private String userState;

    public void setUserId(Long userId) {this.userId = userId;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }
    public void setUserState(String userState) { this.userState = userState; }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", habitName='" + habitName + '\'' +
                ", userState=" + userState + '\'' +
                '}';
    }
}
