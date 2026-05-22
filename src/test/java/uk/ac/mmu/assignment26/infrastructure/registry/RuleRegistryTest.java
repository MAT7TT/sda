package uk.ac.mmu.assignment26.infrastructure.registry;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.rules.hit.ForfeitOnHitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.IgnoreHitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.ExactEndBounceMovementRule;
import uk.ac.mmu.assignment26.domain.rules.movement.StandardEndMovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.IgnoreTeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.WormholeTeleportRule;

class RuleRegistryTest {

  @Test
  void rejectsNullMovementRuleList() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new RuleRegistry(
                null, List.of(new IgnoreHitRule()), List.of(new IgnoreTeleportRule())));
  }

  @Test
  void rejectsNullMovementRuleInsideList() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new RuleRegistry(
                Arrays.asList(new StandardEndMovementRule(), null),
                List.of(new IgnoreHitRule()),
                List.of(new IgnoreTeleportRule())));
  }

  @Test
  void rejectsDuplicateMovementRuleType() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new RuleRegistry(
                List.of(new StandardEndMovementRule(), new StandardEndMovementRule()),
                List.of(new IgnoreHitRule()),
                List.of(new IgnoreTeleportRule())));
  }

  @Test
  void rejectsNullEndRuleTypeLookup() {
    assertThrows(IllegalArgumentException.class, () -> createRegistry().getMovementRule(null));
  }

  @Test
  void rejectsNullHitRuleTypeLookup() {
    assertThrows(IllegalArgumentException.class, () -> createRegistry().getHitRule(null));
  }

  @Test
  void rejectsNullTeleportRuleTypeLookup() {
    assertThrows(IllegalArgumentException.class, () -> createRegistry().getTeleportRule(null));
  }

  private RuleRegistry createRegistry() {
    return new RuleRegistry(
        List.of(new StandardEndMovementRule(), new ExactEndBounceMovementRule()),
        List.of(new IgnoreHitRule(), new ForfeitOnHitRule()),
        List.of(new IgnoreTeleportRule(), new WormholeTeleportRule()));
  }
}
