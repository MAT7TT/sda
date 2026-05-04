package uk.ac.mmu.assignment26.infrastructure.output;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ConsoleGameOutputWriter implements GameOutputWriter {

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }

    @Override
    public void writeBlankLine() {
        System.out.println();
    }
}