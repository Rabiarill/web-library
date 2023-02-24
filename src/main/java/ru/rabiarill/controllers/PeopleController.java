package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rabiarill.dao.BookDAO;
import ru.rabiarill.dao.PersonDAO;
import ru.rabiarill.models.Person;
import ru.rabiarill.util.validators.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

   private final PersonDAO personDAO;
   private final BookDAO bookDAO;
   private final PersonValidator personValidator;

   @Autowired
   public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
      this.personDAO = personDAO;
      this.bookDAO = bookDAO;
      this.personValidator = personValidator;
   }

   @GetMapping
   public String index(Model model){
      model.addAttribute("people", personDAO.personList());
      return "/people/index";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable("id") int id, Model model){
      model.addAttribute("person", personDAO.getPerson(id));
      model.addAttribute("books", bookDAO.bookList(id));
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
      personDAO.save(person);
      return "redirect:/people";
   }

   @GetMapping("/{id}/edit")
   public String edit(@PathVariable int id, Model model){
      model.addAttribute("person", personDAO.getPerson(id));
      return "/people/edit";
   }
   @PatchMapping("/{id}")
   public String update(@PathVariable int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
      personValidator.validate(person, bindingResult);
      if(bindingResult.hasErrors()){
         return "/people/edit";
      }
      personDAO.update(id, person);
      return "redirect:/people/"+id;
   }

   @DeleteMapping("/{id}")
   public String delete(@PathVariable int id){
      personDAO.delete(id);
      return "redirect:/people";
   }

}
