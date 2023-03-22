package ru.rabiarill.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(name = "username")
   @NotEmpty(message = "Enter your username")
   private String username;

   @Column(name = "password")
   @Size(min = 8, message = "Size should be more than 8")
   private String password;

   @Column(name = "full_name")
   @NotEmpty(message = "Enter your full name")
   private String fullName;

   @Column(name = "year_of_birth")
   @Min(value = 1920, message = "Year of birth should be more than 1920")
   private int yearOfBirth;

   @OneToMany(mappedBy = "owner")
   private List<Book> books;

   @Column(name = "role")
   private String role;

   public Person() {
   }

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

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
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

   public List<Book> getBooks() {
      return books;
   }

   public void setBooks(List<Book> books) {
      this.books = books;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public boolean isEmployee() {
      return this.role.equals("ROLE_EMPLOYEE");
   }
}
