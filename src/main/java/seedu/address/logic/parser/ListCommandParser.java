package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOwnerCommand;
import seedu.address.logic.commands.ListPetCommand;

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
    public ListCommand parse(String userInput) {
        userInput = userInput.trim();
        if (userInput.equals("pets")) {
            return new ListPetCommand();
        } else {
            return new ListOwnerCommand();
        }
    }
}
