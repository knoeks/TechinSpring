package lt.techin.demo.service;

import lt.techin.demo.model.Registration;
import lt.techin.demo.model.RunningEvent;
import lt.techin.demo.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunningEventService {
  private final RunningEventRepository runningEventRepository;

  @Autowired
  public RunningEventService(RunningEventRepository runningEventRepository) {
    this.runningEventRepository = runningEventRepository;
  }

  public RunningEvent saveRunningEvent(RunningEvent runningEvent) {
    return runningEventRepository.save(runningEvent);
  }

  public Optional<RunningEvent> findRunningEventById(Long id) {
    return runningEventRepository.findById(id);
  }


  public List<RunningEvent> findAllRunningEvents() {
    return runningEventRepository.findAll();
  }

  public void deleteRunningEventById(Long id) {
    runningEventRepository.deleteById(id);
  }
}
