package ru.rabiarill.util.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rabiarill.dao.PersonDAO;
import ru.rabiarill.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
   private final PersonDAO personDAO;

   @Autowired
   public PersonValidator(PersonDAO personDAO) {
      this.personDAO = personDAO;
   }

   @Override
   public boolean supports(Class<?> clazz) {
      return Person.class.equals(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {
      Person person = (Person) target;
      Optional<Person> personDb =personDAO.getPersonByName(person.getFullName());
      if(personDb.isPresent() && personDb.get().getId() != person.getId()){
            errors.rejectValue("fullName", "", "Person with this name is exist");
      }
   }
}
