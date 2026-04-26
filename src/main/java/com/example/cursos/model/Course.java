package com.example.cursos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSE_ID")
    private Long id;

    @NotBlank(message = "Nome do curso é obrigatório")
    @Column(name = "COURSE_NAME", nullable = false)
    private String name;

    @NotNull(message = "Duração é obrigatória")
    @Positive(message = "Duração deve ser maior que zero")
    @Column(name = "DURATION_HOURS", nullable = false)
    private Integer duration;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @NotNull(message = "Data de lançamento é obrigatória")
    @FutureOrPresent(message = "Data de lançamento deve ser hoje ou uma data futura")
    @Column(name = "RELEASE_DATE", nullable = false)
    private LocalDate releaseDate;

    private String description;

    @Transient
    private boolean favorited;

    public boolean isFavorited() { return favorited; }
    public void setFavorited(boolean favorited) { this.favorited = favorited; }


    public Course() {}

    public Course(String name, Integer duration, BigDecimal price, LocalDate releaseDate) {
        this.name = name;
        this.duration = duration;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
}