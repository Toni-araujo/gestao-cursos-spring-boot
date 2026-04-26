package com.example.cursos.controller;

import com.example.cursos.model.Comment;
import com.example.cursos.model.Course;
import com.example.cursos.model.Favorite;
import com.example.cursos.model.User;
import com.example.cursos.repository.CommentRepository;
import com.example.cursos.repository.FavoriteRepository;
import com.example.cursos.repository.UserRepository;
import com.example.cursos.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository; // Adicionado para os comentários

    // Construtor atualizado com todas as dependências
    public CourseController(CourseService courseService,
                            FavoriteRepository favoriteRepository,
                            UserRepository userRepository,
                            CommentRepository commentRepository) {
        this.courseService = courseService;
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // Método auxiliar corrigido para obter o usuário logado
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String username = auth.getName();
            return userRepository.findUserWithRoleByName(username).orElse(null);
        }
        return null;
    }

    // Listar todos os cursos
    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.findAll();
        User currentUser = getCurrentUser();

        if (currentUser != null) {
            List<Favorite> favorites = favoriteRepository.findByUser(currentUser);
            Set<Long> favoriteIds = favorites.stream()
                    .map(f -> f.getCourse().getId())
                    .collect(Collectors.toSet());
            courses.forEach(course -> course.setFavorited(favoriteIds.contains(course.getId())));
        }

        model.addAttribute("courses", courses);
        return "courses";
    }

    // Listar cursos favoritos
    @GetMapping("/favorites")
    public String listFavorites(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        List<Favorite> favorites = favoriteRepository.findByUser(currentUser);
        model.addAttribute("favorites", favorites);
        return "favorites";
    }

    // Alternar favorito (adicionar/remover)
    @PostMapping("/favorite/{id}")
    public String toggleFavorite(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Course course = courseService.findById(id).orElse(null);
        if (course == null) {
            return "redirect:/courses";
        }

        Optional<Favorite> existing = favoriteRepository.findByUserAndCourse(currentUser, course);
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
        } else {
            Favorite favorite = new Favorite(currentUser, course);
            favoriteRepository.save(favorite);
        }
        return "redirect:/courses";
    }

    // Exibir formulário de adicionar curso
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        return "add_course";
    }

    // Salvar novo curso
    @PostMapping("/add")
    public String addCourse(@Valid @ModelAttribute("course") Course course,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "add_course";
        }
        courseService.save(course);
        return "redirect:/courses";
    }

    // Exibir formulário de edição
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + id));
        model.addAttribute("course", course);
        return "edit_course";
    }

    // Atualizar curso
    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("course") Course course,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "edit_course";
        }
        course.setId(id);
        courseService.save(course);
        return "redirect:/courses";
    }

    // Excluir curso
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return "redirect:/courses";
    }

    // ==========================================
    // MÉTODOS DE COMENTÁRIOS ADICIONADOS AQUI
    // ==========================================

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + id));
        model.addAttribute("course", course);
        model.addAttribute("comments", commentRepository.findByCourseIdOrderByCreatedAtDesc(id));
        return "course_details"; // Retorna a nova página HTML que vamos criar
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content) {
        User user = getCurrentUser();
        // Só salva se o usuário estiver logado e o comentário não estiver vazio
        if (user != null && !content.isBlank()) {
            Course course = courseService.findById(id).orElseThrow();
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCourse(course);
            comment.setUser(user);
            commentRepository.save(comment);
        }
        return "redirect:/courses/" + id;
    }
}