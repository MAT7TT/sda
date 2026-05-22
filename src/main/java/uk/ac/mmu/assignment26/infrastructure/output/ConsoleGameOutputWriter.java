package uk.ac.mmu.assignment26.infrastructure.output;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Console implementation of the game output writer.
 */
@Component
@Primary
public class ConsoleGameOutputWriter implements GameOutputWriter {

  /**
   * Writes one line to standard output.
   *
   * @param message the message to write
   */
  @Override
  public void writeLine(String message) {
    System.out.println(message);
  }

  /**
   * Writes a blank line to standard output.
   */
  @Override
  public void writeBlankLine() {
    System.out.println();
  }
}
