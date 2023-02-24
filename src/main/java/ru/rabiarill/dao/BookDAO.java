package ru.rabiarill.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;
import ru.rabiarill.util.mappers.BookRowMapper;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
   private final JdbcTemplate jdbcTemplate;

   @Autowired
   public BookDAO(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public List<Book> bookList(){
      return jdbcTemplate.query("SELECT * FROM Book", new BookRowMapper() );
   }

   public List<Book> bookList(int personId){
      return jdbcTemplate.query("SELECT * FROM book where person_id=?", new Object[]{personId},
              new BookRowMapper());
   }

   public Book getBook(int id){
      return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BookRowMapper())
              .stream().findAny().orElse(null);
   }

   public void save(Book book) {
      jdbcTemplate.update("INSERT INTO book(name, author, year_of_publishing) " +
                      "VALUES (?, ?, ?)",
              book.getName(), book.getAuthor(), book.getYearOfPublishing()
              );
   }

   public void assign(int bookId, int personId){
      jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personId, bookId);
   }

   public void update(int id, Book book ){
      jdbcTemplate.update("UPDATE book SET  name=?, author=?, year_of_publishing=? WHERE id=?",
              book.getName(), book.getAuthor(), book.getYearOfPublishing(), id);
   }

   public void delete(int id){
      jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
   }

   public void release(int id){
      jdbcTemplate.update("UPDATE book SET person_id=null where id=?", id);
   }

   public Optional<Person> getOwner(int personId) {
      return jdbcTemplate.query("SELECT p.* from  person p JOIN book b ON p.id = b.person_id WHERE person_id=?",
              new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class))
              .stream().findAny();
   }
}
