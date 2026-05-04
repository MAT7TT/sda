package uk.ac.mmu.assignment26.domain.rules.result;

public record HitResult(
        boolean hit,
        String hitPlayerName,
        int hitPosition,
        int from,
        int to
) {
    public boolean playerMovedBack() {
        return hit && from != to;
    }

    public static HitResult noHit(int position) {
        return new HitResult(false, null, position, position, position);
    }

    public static HitResult hitIgnored(String hitPlayerName, int position) {
        return new HitResult(true, hitPlayerName, position, position, position);
    }

    public static HitResult hitAndMovedBack(String hitPlayerName, int from, int to) {
        return new HitResult(true, hitPlayerName, from, from, to);
    }
}