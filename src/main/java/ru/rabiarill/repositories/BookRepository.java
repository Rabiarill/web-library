package ru.rabiarill.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
   List<Book> findByOwner(Person owner);

   List<Book> findAllByNameStartingWith(String name);

   @Modifying
   @Query(value = "update book set return_date=now()+interval '10 days' where id=?1",
           nativeQuery = true)
   void setReturnDate(int bookId);
}
