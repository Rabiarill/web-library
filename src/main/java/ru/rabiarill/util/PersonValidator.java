package ru.rabiarill.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rabiarill.models.Person;
import ru.rabiarill.security.PersonDetails;
import ru.rabiarill.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

   private final PeopleService peopleService;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   public PersonValidator(PeopleService peopleService, PasswordEncoder passwordEncoder) {
      this.peopleService = peopleService;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return Person.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      Person person = (Person) target;
      Optional<Person> personDb = peopleService.findByUsername(person.getUsername());

      if (personDb.isPresent() && personDb.get().getId() != person.getId()) {
         errors.rejectValue("username", "", "Person with this username is exist");
      }
   }

   public void validatePassword(Person personDb, String checkedPassword, Errors errors){
      if (!passwordEncoder.matches(checkedPassword, personDb.getPassword()))
         errors.reject("password", "Incorrect password");
   }

   public void validateAccess(Person person, Errors errors){
      PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext()
              .getAuthentication().getPrincipal();
      Person sender = personDetails.getPerson();

      if (!sender.isEmployee() && sender.getId() != person.getId()) {
         errors.reject("access", "Your role should be \"EMPLOYEE\"");
      }
   }
}
