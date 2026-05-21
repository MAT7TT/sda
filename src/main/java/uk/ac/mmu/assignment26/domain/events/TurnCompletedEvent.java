package uk.ac.mmu.assignment26.domain.events;

import uk.ac.mmu.assignment26.domain.rules.result.TurnResult;

public record TurnCompletedEvent(TurnResult result) {}
