package uk.ac.mmu.assignment26.infrastructure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;

@Component
public class ApplicationGameEventPublisher implements GameEventPublisher {
  private final ApplicationEventPublisher publisher;

  public ApplicationGameEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void publish(Object event) {
    publisher.publishEvent(event);
  }
}
