package com.greenfoxacademy.herokutodo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Assignee {

  @Id
  private String name;
  private String email;

  @OneToMany(mappedBy = "assignee")
  private List<Todo> todos = new ArrayList<>();

  // Default constructor
  public Assignee() {
  }

  public Assignee(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Todo> getTodos() {
    return todos;
  }

  public void setTodos(List<Todo> todos) {
    this.todos = todos;
  }
}
