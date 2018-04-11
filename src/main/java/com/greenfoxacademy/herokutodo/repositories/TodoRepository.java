package com.greenfoxacademy.herokutodo.repositories;

import com.greenfoxacademy.herokutodo.models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long>{

  List<Todo> findAllByDoneIsFalseOrderById();
  List<Todo> findAllByOrderById();

}
