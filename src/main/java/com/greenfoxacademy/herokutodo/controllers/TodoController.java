package com.greenfoxacademy.herokutodo.controllers;

import com.greenfoxacademy.herokutodo.models.Assignee;
import com.greenfoxacademy.herokutodo.models.Todo;
import com.greenfoxacademy.herokutodo.repositories.AssigneeRepository;
import com.greenfoxacademy.herokutodo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private AssigneeRepository assigneeRepository;

  @GetMapping(value = {"/"})
  public String showTodos(@RequestParam(name = "isActive", required = false) boolean isActive, Model model) {
    if (isActive) {
      model.addAttribute("todos", todoRepository.findAllByDoneIsFalseOrderById());
      return "todoslist";
    } else {
      model.addAttribute("todos", todoRepository.findAllByOrderById());
      return "todoslist";
    }
  }

  @GetMapping(value = "addtodo")
  public String showAddPage(Model model) {
    model.addAttribute("todos", todoRepository.findAllByOrderById());
    return "addtodo";
  }

  @PostMapping("addtodo")
  public String addTodo(@ModelAttribute(name = "title") String title) {
    if (!title.isEmpty()) {
      todoRepository.save(new Todo(title));
      return "redirect:/addtodo";
    } else {
      return "redirect:/addtodo";
    }
  }

  @GetMapping("{id}/deletetodo")
  public String deleteTodo(@PathVariable long id) {
    todoRepository.deleteById(id);
    return "redirect:/";
  }

  @GetMapping("{id}/edittodo")
  public String editTodo(@PathVariable long id, Model model) {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      return "redirect:/edittodo";
    } else {
      model.addAttribute("todo", todo);
      model.addAttribute("todos", todoRepository.findAllByOrderById());
      return "edittodo";
    }
  }

  @PostMapping("{id}/edittodo")
  public String editTodo(@ModelAttribute("todo") Todo todo) {
    Todo todoTemp = todoRepository.findById(todo.getId()).orElse(null);
    if (!todo.getTitle().isEmpty()) {
      todoRepository.save(todo);
    } else {
      todo.setTitle(todoTemp.getTitle());
      todoRepository.save(todo);
    }
    return "redirect:/";
  }

  @GetMapping("searchtodo")
  public String searchTodo(@ModelAttribute(name = "title") String title, Model model) {
    if (!title.isEmpty()) {
      model.addAttribute("todos", todoRepository.customTitleFinder(title));
      return "todoslist";
    } else {
      model.addAttribute("todos", todoRepository.findAllByOrderById());
      return "todoslist";
    }
  }

  @GetMapping("{id}/assigntodo")
  public String assignTodo(@PathVariable long id, Model model) {
    Todo todo = todoRepository.findById(id).orElse(null);
    model.addAttribute("assignees",assigneeRepository.findAll());
    model.addAttribute("todo", todo);
    return "assigntodo";
  }

  @PostMapping("{id}/assigntodo")
  public String assignTodo(
          @ModelAttribute(name = "name") String name,
          @PathVariable(name = "id") long id
  ) {
    List<Assignee> assigneesTemp = assigneeRepository.customNameFinder(name);
    Assignee assigneeTemp = assigneesTemp.get(0);
    Todo todo = todoRepository.findById(id).orElse(null);
    todo.setAssignee(assigneeTemp);
    todoRepository.save(todo);
    return "redirect:/";
  }
}
