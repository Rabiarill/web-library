package ru.rabiarill.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(name = "full_name")
   @NotEmpty(message = "Enter your full name")
   private String fullName;

   @Column(name = "year_of_birth")
   @Min(value = 1920, message = "Year of birth should be more than 1920")
   private int yearOfBirth;

   @OneToMany(mappedBy = "owner")
   private List<Book> books;

   public Person (){}

   public Person(int id, String fullName, int yearOfBirth) {
      this.id = id;
      this.fullName = fullName;
      this.yearOfBirth = yearOfBirth;
   }

   public void update(Person person){
      this.fullName = person.getFullName();
      this.yearOfBirth = person.getYearOfBirth();
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public int getYearOfBirth() {
      return yearOfBirth;
   }

   public void setYearOfBirth(int yearOfBirth) {
      this.yearOfBirth = yearOfBirth;
   }
}
