package ru.rabiarill.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private int id;

   @Column(name = "person_id", insertable = false, updatable = false)
   private Integer personId;

   @ManyToOne
   @JoinColumn(name = "person_id", referencedColumnName = "id")
   private Person owner;

   @NotEmpty(message = "Name should not be empty")
   @Size(max = 150, message = "Size should be less than 150")
   @Column(name = "name")
   private String name;

   @Size(max = 150, message = "Size should be less than 150")
   @Column(name = "author")
   private String author;

   @Min(value = 1000, message = "Year of publishing should be more than 1000")
   @Column(name = "year_of_publishing")
   private int yearOfPublishing;

   @Column(name = "return_date")
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = "dd/MM/YYYY hh/mm")
   private Date returnDate;

   public Book() {
   }

   public Book(int id, String name, String author, int yearOfPublishing) {
      this.id = id;
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

   public Person getOwner() {
      return this.owner;
   }

   public void setOwner(Person person) {
      this.owner = person;
   }

   public Integer getPersonId() {
      return personId;
   }

   public void setPersonId(Integer personId) {
      this.personId = personId;
   }

   public Date getReturnDate() {
      return returnDate;
   }

   public void setReturnDate(Date dateOfTake) {
      this.returnDate = dateOfTake;
   }

   public boolean isReturnOverdue() {
      return returnDate.before(new Date());
   }
}
