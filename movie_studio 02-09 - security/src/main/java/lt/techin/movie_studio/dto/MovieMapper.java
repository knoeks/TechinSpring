package lt.techin.movie_studio.dto;

import lt.techin.movie_studio.model.Actor;
import lt.techin.movie_studio.model.Movie;
import lt.techin.movie_studio.model.Screening;
import org.springframework.data.domain.Page;

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

  public static MoviePutDTO toMoviePutDTO(MovieDTO movieDTO) {
    return new MoviePutDTO(
            movieDTO.title(),
            movieDTO.director(),
            movieDTO.screenings(),
            movieDTO.actors()
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

  public static Page<MovieDTO> toMovieDTOPage(Page<Movie> moviePage) {
    return moviePage.map(MovieMapper::toMovieDTO);
  }

  public static void updateMovieFromDTO(MovieDTO movieDTO, Movie movie) {
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setActors(movieDTO.actors());
    movie.setScreenings(movieDTO.screenings());
  }
}
