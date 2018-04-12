package com.greenfoxacademy.herokutodo.repositories;

import com.greenfoxacademy.herokutodo.models.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long>{

  List<Todo> findAllByDoneIsFalseOrderById();
  List<Todo> findAllByOrderById();

  @Query("select lower(c) from Todo c where c.title like %:searchedtitle% order by c.id asc")
  List<Todo> customTitleFinder(@Param("searchedtitle") String searchedtitle);
}
