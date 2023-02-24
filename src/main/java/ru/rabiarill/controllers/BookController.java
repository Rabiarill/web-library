package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rabiarill.dao.BookDAO;
import ru.rabiarill.dao.PersonDAO;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

   private final BookDAO bookDAO;
   private final PersonDAO personDAO;

   @Autowired
   public BookController(BookDAO bookDAO, PersonDAO personDAO) {
      this.bookDAO = bookDAO;
      this.personDAO = personDAO;
   }

   @GetMapping()
   public String index(Model model){
      model.addAttribute("book", bookDAO.bookList());
      return "/book/index";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable int id, Model model, @ModelAttribute("person") Person person){
      Book book = bookDAO.getBook(id);
      Optional<Person> owner = bookDAO.getOwner(book.getPersonId());
      model.addAttribute("book", book);
      if(owner.isPresent()){
      model.addAttribute("owner", personDAO.getPerson(book.getPersonId()));
      }else {
         model.addAttribute("people", personDAO.personList());
      }
      return "/book/show";
   }

   @PostMapping("/{id}/assign")
   public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
      bookDAO.assign(id,person.getId());
      return "redirect:/book/"+id;
   }

   @GetMapping("/new")
   public String newBook(Model model){
      model.addAttribute("book", new Book());
      return "/book/new";
   }

   @PostMapping("/new")
   public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
      if(bindingResult.hasErrors()){
         return "/book/new";
      }
      bookDAO.save(book);
      return "redirect:/book";
   }

   @GetMapping("{id}/edit")
   public String edit(Model model, @PathVariable("id") int id){
      model.addAttribute("book", bookDAO.getBook(id));
      return "/book/edit";
   }

   @PatchMapping("/{id}")
   public String update(@PathVariable int id,@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){  
      if(bindingResult.hasErrors()){
         return "/book/edit";
      }
      bookDAO.update(id, book);
      return "redirect:/book/"+id;
   }

   @PatchMapping("{id}/release")
   public String release(@PathVariable int id){
      bookDAO.release(id);
      return "redirect:/book";
   }

   @DeleteMapping("/{id}")
   public String delete(@PathVariable int id){
      bookDAO.delete(id);
      return "redirect:/book";
   }

}
