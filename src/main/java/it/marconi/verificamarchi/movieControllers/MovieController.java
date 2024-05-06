package it.marconi.verificamarchi.movieControllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.marconi.verificamarchi.movieDomains.MovieForm;
import it.marconi.verificamarchi.movieServices.MovieService;

@Controller
@RequestMapping("/")
public class MovieController {
    @Autowired
    MovieService movieService;

    @GetMapping("/films")
    public ModelAndView viewCatalog1() {
        return new ModelAndView("catalog-page").addObject("movies", movieService.getAll());
    }

    // non funzionante
    @PostMapping("/films/cerca")
    public ModelAndView viewCatalog(@RequestParam("filter") String filter) {
        if(filter.equals(""))
            return new ModelAndView("catalog-page").addObject("movies", movieService.getAll());

        return new ModelAndView("catalog-page").addObject("movies", movieService.filter(filter));
    }

    @GetMapping("/svuota")
    public ModelAndView emptyCatalog() {
        movieService.empty();

        return new ModelAndView("redirect:/films");
    }

    @GetMapping("/films/nuovo")
    public ModelAndView addNewMovie() {
        return new ModelAndView("new-movie").addObject("movieForm", new MovieForm());
    }

    @PostMapping("/films/nuovo/handler")
    public ModelAndView handlerNewMovie(@ModelAttribute MovieForm movieForm) {
        movieService.addMovie(movieForm);

        return new ModelAndView("redirect:/films/" + movieForm.getId());
    }

    @GetMapping("/films/{id}")
    public ModelAndView movieDetail(@PathVariable("id") int id) {
        Optional<MovieForm> movie = movieService.getById(id);

        if(movie.isPresent())
            return new ModelAndView("movie-detail").addObject("movie", movie.get());

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film non trovato");
    }

    @GetMapping("/films/elimina/{id}")
    public ModelAndView deleteMovie(
        @PathVariable("id") int id,
        RedirectAttributes attr
    ) {
        movieService.deleteById(id);

        attr.addFlashAttribute("deleted", true);
        return new ModelAndView("redirect:/films");
    }
}