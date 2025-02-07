package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.model.Screening;

import java.util.List;

public class MovieMapper {

  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    return movies.stream().map(MovieMapper::toMovieDTO).toList();
  }

  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getDirector(),
            movie.getScreenings(),
            movie.getActors()
    );
  }

  public static Movie toMovie(MovieDTO movieDTO) {
    return new Movie(
            movieDTO.id(),
            movieDTO.title(),
            movieDTO.director(),
            movieDTO.screenings(),
            movieDTO.actors()
    );
  }

}
