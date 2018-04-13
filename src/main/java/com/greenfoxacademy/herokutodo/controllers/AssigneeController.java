package com.greenfoxacademy.herokutodo.controllers;

import com.greenfoxacademy.herokutodo.models.Assignee;
import com.greenfoxacademy.herokutodo.repositories.AssigneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssigneeController {

  @Autowired
  private AssigneeRepository assigneeRepository;

  @GetMapping(value = "assigneeslist")
  public String showAssigneesPage(Model model) {
    model.addAttribute("assignees", assigneeRepository.findAll());
    return "assigneeslist";
  }

  @GetMapping(value = "addassignee")
  public String AddAssignee(Model model) {
    model.addAttribute("assignees", assigneeRepository.findAll());
    return "addassignee";
  }

  @PostMapping("addassignee")
  public String addAssignee(
          @ModelAttribute(name = "name") String name,
          @ModelAttribute(name = "email") String email) {
    if (!name.isEmpty() && !email.isEmpty()) {
      assigneeRepository.save(new Assignee(name, email));
      return "redirect:/addassignee";
    } else {
      return "redirect:/addassignee";
    }
  }

  @GetMapping("searchassignee")
  public String searchAssignee(@ModelAttribute(name = "name") String name, Model model) {
    if (!name.isEmpty()) {
      model.addAttribute("assignees", assigneeRepository.customNameFinder(name));
      return "assigneeslist";
    } else {
      model.addAttribute("assignees", assigneeRepository.findAll());
      return "assigneeslist";
    }
  }
}
