package uk.ac.mmu.assignment26.infrastructure.output;

/**
 * Output abstraction for writing game text.
 *
 * <p>This keeps console writing behind an interface so output formatting classes do
 * not depend directly on {@code System.out}.</p>
 */
public interface GameOutputWriter {
  /**
   * Writes one line of output.
   *
   * @param message the message to write
   */
  void writeLine(String message);

  /**
   * Writes a blank line.
   */
  void writeBlankLine();
}
