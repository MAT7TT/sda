package uk.ac.mmu.assignment26.domain.events;

public record GameWonEvent(String playerName, int playerTurns, int totalTurns) {}
