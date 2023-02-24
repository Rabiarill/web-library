package ru.rabiarill.util.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.rabiarill.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {
   @Override
   public Book mapRow(ResultSet resultSet, int i) throws SQLException {
       Book book = new Book();

       book.setId(resultSet.getInt("id"));
       book.setPersonId(resultSet.getInt("person_id"));
       book.setName(resultSet.getString("name"));
       book.setAuthor(resultSet.getString("author"));
       book.setYearOfPublishing(resultSet.getInt("year_of_publishing"));

       return book;
   }
}
