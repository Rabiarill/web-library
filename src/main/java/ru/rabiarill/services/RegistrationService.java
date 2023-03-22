package ru.rabiarill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rabiarill.models.Person;
import ru.rabiarill.repositories.PeopleRepository;

@Service
public class RegistrationService {
   private final PeopleRepository peopleRepository;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
      this.peopleRepository = peopleRepository;
      this.passwordEncoder = passwordEncoder;
   }

   public void register(Person person, String role) {
      person.setPassword(passwordEncoder.encode(person.getPassword()));
      person.setRole(role);
      peopleRepository.save(person);
   }
}
