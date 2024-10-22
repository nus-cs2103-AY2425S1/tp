package spleetwaise.transaction.logic.parser;

import spleetwaise.address.logic.parser.exceptions.ParseException;
import spleetwaise.commons.logic.commands.Command;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format.
     */
    T parse(String userInput) throws ParseException;
}
