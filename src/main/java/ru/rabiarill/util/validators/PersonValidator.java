package ru.rabiarill.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rabiarill.models.Person;
import ru.rabiarill.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

   private final PeopleService peopleService;

   @Autowired
   public PersonValidator(PeopleService peopleService) {
      this.peopleService = peopleService;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return Person.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      Person person = (Person) target;
      Optional<Person> personDb = peopleService.findByName(person.getFullName());
      if(personDb.isPresent() && personDb.get().getId() != person.getId()){
            errors.rejectValue("fullName", "", "Person with this name is exist");
      }
   }
}
