package com.example.cursos.repository;

import com.example.cursos.model.Favorite;
import com.example.cursos.model.User;
import com.example.cursos.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndCourse(User user, Course course);
    void deleteByUserAndCourse(User user, Course course);
}