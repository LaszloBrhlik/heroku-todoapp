package com.greenfoxacademy.herokutodo.repositories;

import com.greenfoxacademy.herokutodo.models.Assignee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssigneeRepository extends CrudRepository<Assignee, String> {

  @Query("select lower(a) from Assignee a where a.name like %:searchedName% order by a.name asc")
  List<Assignee> customNameFinder(@Param("searchedName") String searchedName);

  @Query("select lower(a) from Assignee a where a.email like %:searchedEmail% order by a.name asc")
  List<Assignee> customEmailFinder(@Param("searchedEmail") String searchedEmail);
}
