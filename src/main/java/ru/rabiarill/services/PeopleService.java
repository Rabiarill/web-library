package ru.rabiarill.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rabiarill.models.Person;
import ru.rabiarill.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

   private final PeopleRepository peopleRepository;

   @Autowired
   public PeopleService(PeopleRepository peopleRepository) {
      this.peopleRepository = peopleRepository;
   }

   public List<Person> findAll(){
      return peopleRepository.findAll();
   }

   public Person findOne(int id){
      return peopleRepository.findById(id).orElse(null);
   }

   public Optional<Person> findByName(String name){
      return peopleRepository.findByFullName(name);
   }

   @Transactional
   public void save(Person person){
      peopleRepository.save(person);
   }

   @Transactional
   public void update(int id, Person updatedPerson){
      updatedPerson.setId(id);
      peopleRepository.save(updatedPerson);
   }

   @Transactional
   public void delete(int id){
      peopleRepository.deleteById(id);
   }
}
