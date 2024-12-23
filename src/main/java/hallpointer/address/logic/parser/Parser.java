package hallpointer.address.logic.parser;

import hallpointer.address.logic.commands.Command;
import hallpointer.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform to the expected format
     */
    T parse(String userInput) throws ParseException;
}
