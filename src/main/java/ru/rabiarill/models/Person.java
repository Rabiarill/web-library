package ru.rabiarill.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Person {
   private int id;
   @NotEmpty(message = "Enter your full name")
   private String fullName;
   @Min(value = 1920, message = "Year of birth should be more than 1920")
   private int yearOfBirth;

   public Person (){}

   public Person(int id, String fullName, int yearOfBirth) {
      this.id = id;
      this.fullName = fullName;
      this.yearOfBirth = yearOfBirth;
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
