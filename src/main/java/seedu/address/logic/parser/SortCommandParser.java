package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortOwnerCommand;
import seedu.address.logic.commands.SortPetCommand;

/**
 * Parses user input for the 'list' command and returns the appropriate command
 * (either {@code ListOwnerCommand} or {@code ListPetCommand}).
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code userInput} and returns the corresponding sort command.
     * If the input is "pets", it returns {@code SortPetCommand}, otherwise it returns {@code SortOwnerCommand}.
     *
     * @param userInput The input from the user to be parsed.
     * @return A {@code SortCommand} based on the user input.
     */
    public SortCommand parse(String userInput) {
        userInput = userInput.trim();
        if (userInput.equals("pets")) {
            return new SortPetCommand();
        } else if (userInput.equals("owners")) {
            return new SortOwnerCommand();
        } else {
            return null; //fix later
        }
    }
}
