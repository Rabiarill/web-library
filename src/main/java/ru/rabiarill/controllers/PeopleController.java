package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rabiarill.models.Person;
import ru.rabiarill.security.PersonDetails;
import ru.rabiarill.services.BookService;
import ru.rabiarill.services.PeopleService;
import ru.rabiarill.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

   private final PeopleService peopleService;
   private final BookService bookService;
   private final PersonValidator personValidator;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator, PasswordEncoder passwordEncoder) {
      this.peopleService = peopleService;
      this.bookService = bookService;
      this.personValidator = personValidator;
      this.passwordEncoder = passwordEncoder;
   }

   @GetMapping
   public String index(Model model) {
      model.addAttribute("people", peopleService.findAll());
      return "/people/index";
   }

   @GetMapping("/info")
   public String infoPage(Model model) {
      PersonDetails personDetails = (PersonDetails) SecurityContextHolder
              .getContext().getAuthentication().getPrincipal();
      Person person = personDetails.getPerson();

      model.addAttribute("person", person);
      model.addAttribute("books", bookService.findByOwner(person));
      return "/people/info";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable("id") int id, Model model) {
      Person person = peopleService.findOne(id);
      model.addAttribute("person", person);
      model.addAttribute("books", bookService.findByOwner(person));
      return "people/show";
   }

   @GetMapping("/{id}/edit")
   public String edit(@PathVariable int id, Model model) {
      model.addAttribute("person", peopleService.findOne(id));
      return "/people/edit";
   }

   @GetMapping("/{id}/edit/password")
   public String changePasswordPage(@PathVariable("id") int id, Model model) {
      model.addAttribute("person", peopleService.findOne(id));
      return "/people/edit_password";
   }

   @PatchMapping("/{id}/edit/password")
   public String editPassword(@PathVariable("id") int id,
                              @ModelAttribute("person") @Valid Person person,
                              BindingResult bindingResult,
                              @ModelAttribute("old_password") String oldPassword) {
      String newPassword = person.getPassword();
      person = peopleService.findOne(id);

      personValidator.validateAccess(person, bindingResult);
      personValidator.validatePassword(person, oldPassword, bindingResult);

      if (bindingResult.hasErrors()) {
         return "/people/edit_password";
      }

      person.setPassword(passwordEncoder.encode(newPassword));
      peopleService.save(person);

      return "redirect:/auth/login";
   }

   @PatchMapping("/{id}/edit")
   public String update(@PathVariable int id,
                        @ModelAttribute("person") @Valid Person person,
                        BindingResult bindingResult) {
      personValidator.validate(person, bindingResult);
      personValidator.validateAccess(person, bindingResult);

      if (bindingResult.hasErrors()) {
         return "/people/edit";
      }
      peopleService.update(id, person);
      return "/main";
   }

   @DeleteMapping("/{id}/delete")
   public String delete(@PathVariable int id) {
      peopleService.delete(id);
      return "/main";
   }

}
