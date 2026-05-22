package uk.ac.mmu.assignment26.domain.events;

/**
 * Event published when the game changes state.
 *
 * @param from the previous state name
 * @param to the new state name
 */
public record GameStateChangedEvent(String from, String to) {}
