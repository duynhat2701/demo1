package com.example.spring_demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // VALIDATION
    @NotBlank(message = "Title không được để trống")
    private String title;

    private String description;

    @NotNull(message = "Status không được null")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate deadline;

    private LocalDateTime createdAt;

    // 🔗 RELATIONSHIP: nhiều task thuộc 1 user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {}

    // AUTO SET TIME
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTER SETTER =====

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}