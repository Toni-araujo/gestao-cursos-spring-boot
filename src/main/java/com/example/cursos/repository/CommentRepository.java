package com.example.cursos.repository;

import com.example.cursos.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCourseIdOrderByCreatedAtDesc(Long courseId);
}