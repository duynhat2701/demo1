package com.example.spring_demo.service;

import com.example.spring_demo.entity.Task;
import com.example.spring_demo.entity.Status;
import com.example.spring_demo.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private Repository repo;

    // GET with filter + search
    public List<Task> getAll(Status status, String keyword) {

        if (status != null && keyword != null) {
            return repo.findByStatusAndTitleContainingIgnoreCase(status, keyword);
        }

        if (status != null) {
            return repo.findByStatus(status);
        }

        if (keyword != null) {
            return repo.findByTitleContainingIgnoreCase(keyword);
        }

        return repo.findAll();
    }

    // GET by id
    public Task getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // CREATE
    public Task create(Task task) {
        return repo.save(task);
    }

    // UPDATE
    public Task update(Long id, Task newTask) {
        Task task = getById(id);

        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setStatus(newTask.getStatus());
        task.setDeadline(newTask.getDeadline());

        return repo.save(task);
    }

    // DELETE
    public void delete(Long id) {
        repo.deleteById(id);
    }
}