package com.example.app.web.admin;

import com.example.app.domain.genre.GenreService;
import com.example.app.domain.genre.genre.GenreDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GenreManagementController {
    private final GenreService genreService;

    public GenreManagementController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/admin/dodaj-gatunek")
    public String addGenreForm(Model model) {
        GenreDto genre = new GenreDto();
        model.addAttribute("genre", genre);
        return "admin/genre-add-form";
    }

    @PostMapping("/admin/dodaj-gatunek")
    public String addGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.addGenre(genre);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został zapisany".formatted(genre.getName())
        );
        return "redirect:/admin";
    }

    @GetMapping("/admin/edytuj-gatunek")
    public String editGenreForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();

        model.addAttribute("allGenres", allGenres);
        return "admin/genre-edit-form";
    }

    @PostMapping("/admin/edytuj-gatunek")
    public String editGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.saveEditedGenre(genre);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został zapisany".formatted(genre.getName())
        );
        return "redirect:/admin";
    }

    @GetMapping("/admin/usun-gatunek")
    public String deleteGenreForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("allGenres", allGenres);
        return "admin/genre-delete-form";
    }

    @PostMapping("/admin/usun-gatunek")
    public String deleteGenre(Long genreId, RedirectAttributes redirectAttributes) {
        GenreDto deletedGenre = genreService.deleteGenreById(genreId);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Gatunek %s został usunięty".formatted(deletedGenre.getName())
        );
        return "redirect:/admin";
    }
}
