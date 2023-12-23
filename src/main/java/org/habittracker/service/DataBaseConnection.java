package org.habittracker.service;

import com.jayway.jsonpath.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.habittracker.model.User;
import org.habittracker.model.Habit;
import org.hibernate.criterion.Restrictions;
import org.hibernate.*;

import java.util.HashMap;
import java.util.List;

public class DataBaseConnection {
    private SessionFactory sessionFactory;
    private Configuration configuration;
    public DataBaseConnection(){
        configuration = new Configuration().configure();
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
                user.setChatId(userId);
                user.setUserName(String.valueOf(userId));
                user.setChatId(update.getMessage().getChatId());
                session.save(user);
                transaction.commit();
            }
        }
    }
    public User getUserById(String userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        }
    }
    public void addTask(Update update, String name, String userName){
        Habit userTask = new Habit();
        userTask.setUserId(update.getMessage().getFrom().getId());
        userTask.setHabitId(name);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userTask);
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
    public List<String> nameTask(Long userId){
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT task.taskId FROM UserTask task WHERE task.responsibleId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<String> taskNames = query.list();

            return taskNames;
        }
    }
    public List<String> nameTaskCreator(Long userId){
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT task.taskId FROM UserTask task WHERE task.creatorId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<String> taskNames = query.list();
            return taskNames;
        }
    }

    /*public void deleteTaskByTaskName(String taskName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Habit.class);
            criteria.add(Restrictions.eq("taskId", taskName));
            Habit task = (Habit) criteria.uniqueResult();
            session.delete(task);
            session.getTransaction().commit();
        }

    }*/
    public List<Habit> getTasksByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM UserTask WHERE creatorId = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<Habit> tasks = query.list();
            return tasks;
        }
    }

    public User getUserById(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        }
    }

}