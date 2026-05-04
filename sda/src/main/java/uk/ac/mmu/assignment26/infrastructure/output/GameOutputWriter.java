package uk.ac.mmu.assignment26.infrastructure.output;

public interface GameOutputWriter {
    void writeLine(String message);

    void writeBlankLine();
}