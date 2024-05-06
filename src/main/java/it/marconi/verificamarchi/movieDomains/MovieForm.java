package it.marconi.verificamarchi.movieDomains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieForm {
    private int id;
    private String name;
    private String category;
    private int year;
    private int score;
}