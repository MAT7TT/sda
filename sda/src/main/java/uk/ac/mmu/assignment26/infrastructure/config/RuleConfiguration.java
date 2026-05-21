package uk.ac.mmu.assignment26.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.assignment26.domain.rules.hit.ForfeitOnHitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.IgnoreHitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.ExactEndBounceMovementRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.movement.StandardEndMovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.IgnoreTeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.WormholeTeleportRule;

@Configuration
public class RuleConfiguration {

  @Bean
  public MovementRule standardEndMovementRule() {
    return new StandardEndMovementRule();
  }

  @Bean
  public MovementRule exactEndBounceMovementRule() {
    return new ExactEndBounceMovementRule();
  }

  @Bean
  public HitRule ignoreHitRule() {
    return new IgnoreHitRule();
  }

  @Bean
  public HitRule forfeitOnHitRule() {
    return new ForfeitOnHitRule();
  }

  @Bean
  public TeleportRule ignoreTeleportRule() {
    return new IgnoreTeleportRule();
  }

  @Bean
  public TeleportRule wormholeTeleportRule() {
    return new WormholeTeleportRule();
  }
}
