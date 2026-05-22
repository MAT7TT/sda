package uk.ac.mmu.assignment26.domain.events;

import uk.ac.mmu.assignment26.domain.rules.result.TurnResult;

/**
 * Event published after a player completes a turn.
 *
 * @param result the completed turn result
 */
public record TurnCompletedEvent(TurnResult result) {}
