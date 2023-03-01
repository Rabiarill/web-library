package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rabiarill.models.Person;
import ru.rabiarill.services.BookService;
import ru.rabiarill.services.PeopleService;
import ru.rabiarill.util.validators.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

   private final PeopleService peopleService;
   private final BookService bookService;
   private final PersonValidator personValidator;

   @Autowired
   public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator) {
      this.peopleService = peopleService;
      this.bookService = bookService;
      this.personValidator = personValidator;
   }

   @GetMapping
   public String index(Model model){
      model.addAttribute("people", peopleService.findAll());
      return "/people/index";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable("id") int id, Model model){
      Person person = peopleService.findOne(id);
      model.addAttribute("person", person);
      model.addAttribute("books", bookService.findByOwner(person));
      return "people/show";
   }

   @GetMapping("/new")
   public String newPerson(Model model){
      model.addAttribute("person", new Person());
      return "/people/new";
   }

   @PostMapping("/new")
   public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
      personValidator.validate(person, bindingResult);
      if (bindingResult.hasErrors()) {
         return "/people/new";
      }
      peopleService.save(person);
      return "redirect:/people";
   }

   @GetMapping("/{id}/edit")
   public String edit(@PathVariable int id, Model model){
      model.addAttribute("person", peopleService.findOne(id));
      return "/people/edit";
   }
   @PatchMapping("/{id}")
   public String update(@PathVariable int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
      personValidator.validate(person, bindingResult);
      if(bindingResult.hasErrors()){
         return "/people/edit";
      }
      peopleService.update(id, person);
      return "redirect:/people/"+id;
   }

   @DeleteMapping("/{id}")
   public String delete(@PathVariable int id){
      peopleService.delete(id);
      return "redirect:/people";
   }

}
