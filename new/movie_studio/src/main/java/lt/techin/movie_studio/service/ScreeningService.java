package lt.techin.movie_studio.service;

import lt.techin.movie_studio.model.Screening;
import lt.techin.movie_studio.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
  private final ScreeningRepository screeningRepository;

  @Autowired
  public ScreeningService(ScreeningRepository screeningRepository) {
    this.screeningRepository = screeningRepository;
  }

  public List<Screening> findAllScreenings() {
    return screeningRepository.findAll();
  }

  public Optional<Screening> findScreeningById(Long id) {
    return screeningRepository.findById(id);
  }

  public Screening saveScreening(Screening screening) {
    return screeningRepository.save(screening);
  }
}
