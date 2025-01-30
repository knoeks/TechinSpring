package com.example.helloTest.model;

public class Book {
  private String name;
  private String author;

  public Book(String name, String author) {
    this.name = name;
    this.author = author;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
