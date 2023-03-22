package ru.rabiarill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;
import ru.rabiarill.repositories.BookRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

   private final BookRepository bookRepository;

   @Autowired
   public BookService(BookRepository bookRepository) {
      this.bookRepository = bookRepository;
   }

   public List<Book> findAll(boolean sortByYearOfPublishing) {
      if (sortByYearOfPublishing) {
         return bookRepository.findAll(Sort.by("yearOfPublishing"));
      }
      return bookRepository.findAll();
   }

   public List<Book> findAll(Integer page, Integer itemsPerPage, boolean sortByYearOfPublishing) {
      if (sortByYearOfPublishing) {
         return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("yearOfPublishing"))).getContent();
      }
      return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();

   }

   public List<Book> findByOwner(Person owner) {
      return bookRepository.findByOwner(owner);
   }

   public Book findOne(int id) {
      return bookRepository.findById(id).orElse(null);
   }

   @Transactional
   public void save(Book book) {
      bookRepository.save(book);
   }

   @Transactional
   public void update(int id, Book updatedBook) {
      updatedBook.setId(id);
      bookRepository.save(updatedBook);
   }

   @Transactional
   public void delete(int id) {
      bookRepository.deleteById(id);
   }

   @Transactional
   public void release(int id) {
      Book book = bookRepository.getById(id);
      book.setOwner(null);
      book.setReturnDate(null);
   }

   @Transactional
   public void assign(int bookId, Person owner) {
      Book book = bookRepository.getById(bookId);
      book.setOwner(owner);
      bookRepository.setReturnDate(bookId);
   }

   public List<Book> findAllByNameStartingWith(String name) {
      return bookRepository.findAllByNameStartingWith(name);
   }

}
