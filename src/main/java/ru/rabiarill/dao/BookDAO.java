package ru.rabiarill.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

   private final SessionFactory sessionFactory;

   @Autowired
   public BookDAO(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Transactional(readOnly = true)
   public List<Book> bookList(){
      return  sessionFactory.getCurrentSession()
              .createQuery("from Book", Book.class).getResultList();
   }

   @Transactional(readOnly = true)
   public List<Book> bookList(int personId){
      Session session = sessionFactory.getCurrentSession();
      return  session.createQuery("from Book where owner=:owner", Book.class)
              .setParameter("owner", session.get(Person.class, personId)).getResultList();
   }

   @Transactional(readOnly = true)
   public Book getBook(int id){
      return sessionFactory.getCurrentSession().get(Book.class, id);
   }

   @Transactional
   public void save(Book book) {
      sessionFactory.getCurrentSession().save(book);
   }

   @Transactional
   public void update(int id, Book book ){
      sessionFactory.getCurrentSession().get(Book.class, id).update(book);
   }

   @Transactional
   public void assign(int bookId, int personId){
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("update Book set owner=:owner where id=:bookId")
              .setParameter("owner", session.get(Person.class, personId))
              .setParameter("bookId", bookId).executeUpdate();
   }


   @Transactional
   public void delete(int id){
      sessionFactory.getCurrentSession().createQuery("delete Book where id=:id")
              .setParameter("id", id).executeUpdate();
   }

   @Transactional
   public void release(int id){
      sessionFactory.getCurrentSession().createQuery("update Book set owner=null where id=:id")
              .setParameter("id", id).executeUpdate();
   }

   @Transactional(readOnly = true)
   public Optional<Person> getOwner(int personId) {
      return sessionFactory.getCurrentSession().createQuery("from Person where id=:personId", Person.class)
              .setParameter("personId", personId).stream().findAny();
   }
}
