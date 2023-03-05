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

   @Modifying
   @Query("update Book set owner=null where id=?1")
   void setOwner(int id);

   @Modifying
   @Query("update Book b set b.personId=?2 where b.id=?1")
   void setBookOwner(int bookId, int personId);

   List<Book> findAllByNameStartingWith(String name);
}
