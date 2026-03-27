package com.example.spring_demo.repository;

import com.example.spring_demo.entity.Task;
import com.example.spring_demo.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(Status status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    List<Task> findByStatusAndTitleContainingIgnoreCase(Status status, String keyword);
}