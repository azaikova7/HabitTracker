package org.habittracker.model;

import javax.persistence.*;

@Entity
@Table(name = "habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long userId;
    private String habitId;
    public void setUserId(Long userId) { this.userId = userId; }
    public void setHabitId(String habitId) {this.habitId = habitId;}

}
