package org.habittracker.service;

import com.jayway.jsonpath.Criteria;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.habittracker.model.User;
import org.habittracker.service.DataBaseConnection;

import java.util.List;


@SuppressWarnings("ALL")
public class DataBaseConnection {
    private final SessionFactory sessionFactory;

    public DataBaseConnection(){

        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }
    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }


    public void addUser(Update update){
        Long userId = update.getMessage().getFrom().getId();
        User existingUser = getUserById(userId);
        if (existingUser == null) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                User user = new User();
                user.setUserId(userId);
                user.setUserName(update.getMessage().getFrom().getUserName());
                user.setUserId(update.getMessage().getChatId());
                user.setUserState("START");
                session.save(user);
                transaction.commit();
            }
        }
    }
    public User getUserById(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        }
    }
    public void addHabit(Update update, String habit, String userName){
        User userHabit = new User();
        userHabit.setUserId(update.getMessage().getFrom().getId());
        userHabit.setHabitName(habit);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userHabit);
            transaction.commit();
        }
    }

    public void editState(Long userId, String state){
        User user = getUserById(userId);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            user.setUserState(state);
            session.update(user);
            transaction.commit();
        }
    }

    public Long userIdToName(String userName){
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT user.userId FROM User user WHERE user.userName = :userName";
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            Long userId = (Long) query.uniqueResult();
            return userId;
        }
    }
    public List<String> nameHabit(Long userId){
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT user.habitName FROM User user WHERE user.userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<String> habits = query.list();
            return habits;
        }
    }

    public List<String> getHabitsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE userId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<String> habits = query.list();
            return habits;
        }
    }
}