package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListBothCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOwnerCommand;
import seedu.address.logic.commands.ListPetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for the 'list' command and returns the appropriate command
 * (either {@code ListOwnerCommand} or {@code ListPetCommand}).
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code userInput} and returns the corresponding list command.
     * If the input is "pets", it returns {@code ListPetCommand}, otherwise it returns {@code ListOwnerCommand}.
     *
     * @param userInput The input from the user to be parsed.
     * @return A {@code ListCommand} based on the user input.
     */
    public ListCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        if (userInput.equals("pets")) {
            return new ListPetCommand();
        } else if (userInput.equals("owners")) {
            return new ListOwnerCommand();
        } else if (userInput.equals("both")) {
            return new ListBothCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
