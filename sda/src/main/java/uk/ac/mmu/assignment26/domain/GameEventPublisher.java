package uk.ac.mmu.assignment26.domain;

public interface GameEventPublisher {
    void publish(Object event);
}