package ru.rabiarill.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rabiarill.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
   private final JdbcTemplate jdbcTemplate;

   @Autowired
   public PersonDAO(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public List<Person> personList() {
      return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
   }

   public Person getPerson(int id) {
      return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
              .stream().findAny().orElse(null);
   }

   public Optional<Person> getPersonByName(String fullName){
      return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new Object[]{fullName},
              new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
   }

   public void save(Person person) {
      jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)",
              person.getFullName(), person.getYearOfBirth());
   }

   public void update(int id, Person updatedPerson){
      jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? where id = ?",
              updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
   }
   public void delete(int id){
      jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
   }
}
