package uk.ac.mmu.assignment26.domain.events;

public interface GameEventPublisher {
  void publish(Object event);
}
