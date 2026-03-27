package com.example.spring_demo.controller;

import com.example.spring_demo.entity.Task;
import com.example.spring_demo.entity.Status;
import com.example.spring_demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class Controller {

    @Autowired
    private Service service;

    // GET all + filter + search
    @GetMapping
    public List<Task> getAll(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String keyword
    ) {
        return service.getAll(status, keyword);
    }

    // GET by id
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // CREATE
    @PostMapping
    public Task create(@RequestBody Task task) {
        return service.create(task);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return service.update(id, task);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}