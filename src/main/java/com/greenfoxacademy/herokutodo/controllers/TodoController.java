package com.greenfoxacademy.herokutodo.controllers;

import com.greenfoxacademy.herokutodo.models.Todo;
import com.greenfoxacademy.herokutodo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

  @Autowired
  private TodoRepository todoRepository;

  @GetMapping(value = {"/", "/list"})
  public String list(@RequestParam(name = "isActive", required = false) boolean isActive, Model model) {
    if (isActive) {
      model.addAttribute("todos", todoRepository.findAllByDoneIsFalseOrderById());
      return "todoslist";
    } else {
      model.addAttribute("todos", todoRepository.findAllByOrderById());
      return "todoslist";
    }
  }

  @GetMapping(value = "add")
  public String showAddPage(Model model) {
    model.addAttribute("todos", todoRepository.findAllByOrderById());
    return "add";
  }

  @PostMapping("add")
  public String add(@ModelAttribute(name = "title") String title) {
    todoRepository.save(new Todo(title));
    return "redirect:/";
  }

  @GetMapping("{id}/delete")
  public String delete(@PathVariable long id) {
    todoRepository.deleteById(id);
    return "redirect:/";
  }

  @GetMapping("{id}/edit")
  public String edit(@PathVariable long id, Model model) {
    Todo todo = todoRepository.findById(id).orElse(null);
    if (todo == null) {
      return "redirect:/";
    } else {
      model.addAttribute("todo", todo);
      return "edit";
    }
  }

  @PostMapping("{id}/edit")
  public String edit(@ModelAttribute("todo") Todo todo) {
    todoRepository.save(todo);
    return "redirect:/";
  }

  @GetMapping("search")
  public String search(@ModelAttribute(name = "title") String title, Model model) {
    if (!title.isEmpty()) {
      model.addAttribute("todosFiltered", todoRepository.customTitleFinder(title));
      return "todoslist";
    } else {
      model.addAttribute("todos", todoRepository.findAllByOrderById());
      return "todoslist";
    }
  }
}
