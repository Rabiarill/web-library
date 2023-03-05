package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rabiarill.models.Book;
import ru.rabiarill.models.Person;
import ru.rabiarill.services.BookService;
import ru.rabiarill.services.PeopleService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

   private final BookService bookService;
   private final PeopleService peopleService;

   @Autowired
   public BookController(BookService bookService, PeopleService peopleService) {
      this.bookService = bookService;
      this.peopleService = peopleService;
   }

   @GetMapping()
   public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage,
                       @RequestParam(value = "sortByYear", required = false) boolean sortByYear){
      if (page!=null && itemsPerPage!=null){
         model.addAttribute("book", bookService.findAll(page, itemsPerPage, sortByYear));
      }else {
         model.addAttribute("book", bookService.findAll(sortByYear));
      }
      return "/book/index";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable int id, Model model, @ModelAttribute("person") Person person){
      Book book = bookService.findOne(id);
      Optional<Person> owner = Optional.ofNullable(book.getOwner());
      model.addAttribute("book", book);
      if(owner.isPresent()){
         model.addAttribute("owner", owner.get());
      }else {
         model.addAttribute("people", peopleService.findAll());
      }
      return "/book/show";
   }

   @PostMapping("/{id}/assign")
   public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
      bookService.assign(id,person.getId());
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
      bookService.save(book);
      return "redirect:/book";
   }

   @GetMapping("{id}/edit")
   public String edit(Model model, @PathVariable("id") int id){
      model.addAttribute("book", bookService.findOne(id));
      return "/book/edit";
   }

   @PatchMapping("/{id}")
   public String update(@PathVariable int id,@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
      if(bindingResult.hasErrors()){
         return "/book/edit";
      }
      bookService.update(id, book);
      return "redirect:/book/"+id;
   }

   @PatchMapping("{id}/release")
   public String release(@PathVariable int id){
      bookService.release(id);
      return "redirect:/book";
   }

   @DeleteMapping("/{id}")
   public String delete(@PathVariable int id){
      bookService.delete(id);
      return "redirect:/book";
   }

   @GetMapping("/search")
   public String searchPage(){
      return "/book/search";
   }

   @PostMapping("/search")
   public String resultOfSearch(Model model, @RequestParam("bookName") String bookName){
      model.addAttribute("books", bookService.findAllByNameStartingWith(bookName));
      return "/book/search";
   }

}
