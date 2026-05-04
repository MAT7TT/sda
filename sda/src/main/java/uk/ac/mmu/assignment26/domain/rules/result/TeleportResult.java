package uk.ac.mmu.assignment26.domain.rules.result;

public record TeleportResult(
        boolean teleported,
        int from,
        int to
) {
    public static TeleportResult notTeleported(int position) {
        return new TeleportResult(false, position, position);
    }

    public static TeleportResult teleported(int from, int to) {
        return new TeleportResult(true, from, to);
    }
}