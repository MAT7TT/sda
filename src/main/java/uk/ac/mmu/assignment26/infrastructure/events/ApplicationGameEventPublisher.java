package uk.ac.mmu.assignment26.infrastructure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;

/**
 * Spring adapter for publishing domain events.
 *
 * <p>The domain depends on {@link GameEventPublisher}. This adapter
 * forwards domain events to Spring's application event system.</p>
 */
@Component
public class ApplicationGameEventPublisher implements GameEventPublisher {
  private final ApplicationEventPublisher publisher;

  /**
   * Creates the Spring event publisher adapter.
   *
   * @param publisher Spring application event publisher
   */
  public ApplicationGameEventPublisher(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  /**
   * Publishes a domain event through Spring.
   *
   * @param event the event to publish
   */
  @Override
  public void publish(Object event) {
    publisher.publishEvent(event);
  }
}
