package ru.rabiarill.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public class Book {
   private int id;
   private int personId;
   @NotEmpty (message = "Name should not be empty")
   @Size(max = 150, message = "Size should be less than 150")
   private String name;
   @Size(max = 150, message = "Size should be less than 150")
   private String author;
   @Min(value = 1000, message = "Year of publishing should be more than 1000")
   private int yearOfPublishing;

   public Book(){}

   public Book(int id, int personId, String name, String author, int yearOfPublishing) {
      this.id = id;
      this.personId = personId;
      this.name = name;
      this.author = author;
      this.yearOfPublishing = yearOfPublishing;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getPersonId() {
      return personId;
   }

   public void setPersonId(int personId) {
      this.personId = personId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public int getYearOfPublishing() {
      return yearOfPublishing;
   }

   public void setYearOfPublishing(int yearOfPublishing) {
      this.yearOfPublishing = yearOfPublishing;
   }

}
