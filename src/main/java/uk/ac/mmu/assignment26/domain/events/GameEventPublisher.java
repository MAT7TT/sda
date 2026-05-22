package uk.ac.mmu.assignment26.domain.events;

/**
 * Abstraction for publishing domain events.
 *
 * <p>The domain depends on this interface rather than on Spring or the console output layer.
 */
public interface GameEventPublisher {
  /**
   * Publishes a domain event.
   *
   * @param event the event to publish
   */
  void publish(Object event);
}
