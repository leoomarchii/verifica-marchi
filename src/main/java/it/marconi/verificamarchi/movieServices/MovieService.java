package it.marconi.verificamarchi.movieServices;

import java.util.Optional;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import it.marconi.verificamarchi.movieDomains.MovieForm;

@Service
public class MovieService {
    private ArrayList<MovieForm> movies = new ArrayList<>();

    public ArrayList<MovieForm> getAll() {
        return movies;
    }

    public void addMovie(MovieForm newMovie) {
        movies.add(newMovie);
    }

    public Optional<MovieForm> getById(int id) {
        for (MovieForm m : movies)
            if(m.getId() == id)
                return Optional.of(m);
            
        return Optional.empty();
    }

    public void empty() {
        movies.clear();
    }

    public void deleteById(int id) {
        for (MovieForm m : movies)
            if(m.getId() == id){
                movies.remove(m);
                return;
            }
    }

    public ArrayList<MovieForm> filter(String filter) {
        ArrayList<MovieForm> filteredMovies = new ArrayList<>();

        for (MovieForm m : movies)
            if(m.getName().contains(filter))
                filteredMovies.add(m);

        return movies;
    }
}