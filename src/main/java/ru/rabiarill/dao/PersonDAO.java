package ru.rabiarill.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rabiarill.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

   private final SessionFactory sessionFactory;

   @Autowired
   public PersonDAO(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Transactional(readOnly = true)
   public List<Person> personList() {
      return sessionFactory.getCurrentSession()
              .createQuery("from Person", Person.class).getResultList();
   }

   @Transactional(readOnly = true)
   public Person getPerson(int id) {
      return sessionFactory.getCurrentSession().get(Person.class, id);
   }

   @Transactional(readOnly = true)
   public Optional<Person> getPersonByName(String fullName){
      return sessionFactory.getCurrentSession()
              .createQuery("from Person where fullName=:fullName", Person.class)
              .setParameter("fullName", fullName)
              .stream().findAny();
   }

   @Transactional
   public void save(Person person) {
      sessionFactory.getCurrentSession().save(person);
   }

   @Transactional
   public void update(int id, Person updatedPerson){
      sessionFactory.getCurrentSession().get(Person.class, id).update(updatedPerson);
   }

   @Transactional
   public void delete(int id){
      sessionFactory.getCurrentSession().createQuery("delete Person where id=:id")
              .setParameter("id", id).executeUpdate();
   }
}
