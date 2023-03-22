package ru.rabiarill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rabiarill.models.Person;
import ru.rabiarill.services.PeopleService;
import ru.rabiarill.util.PersonValidator;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
   private final PeopleService peopleService;
   private final PersonValidator personValidator;

   @Autowired
   public EmployeeController(PeopleService peopleService, PersonValidator personValidator) {
      this.peopleService = peopleService;
      this.personValidator = personValidator;
   }

   @PostMapping("/{id}/appoint")
   public String appointEmployee(@PathVariable("id") int id) {
      Person person = peopleService.findOne(id);
      person.setRole("ROLE_EMPLOYEE");
      peopleService.save(person);

      return "redirect:/people";
   }

   @PostMapping("/{id}/user")
   public String makeEmployeeUser(@PathVariable("id") int id) {
      Person person = peopleService.findOne(id);
      person.setRole("ROLE_USER");
      peopleService.save(person);

      return "redirect:/people";
   }

}
