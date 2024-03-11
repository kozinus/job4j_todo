package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {

    private final static Logger LOG = LoggerFactory.getLogger(UserStore.class.getName());

    private final SessionFactory sf;

    public Optional<User> save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.of(user);
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.ofNullable(session.createQuery(
                            "from User where email = :fEmail and password = :fPass", User.class)
                    .setParameter("fEmail", email)
                    .setParameter("fPass", password)
                    .getSingleResult());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public Collection<User> findAll() {
        Session session = sf.openSession();
        List<User> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery(
                    "from User order by id asc", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean flag = false;
        try {
            session.beginTransaction();
            flag = session.createQuery("delete User where id = :Id", User.class)
                    .setParameter("Id", id).executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return flag;
    }
}
