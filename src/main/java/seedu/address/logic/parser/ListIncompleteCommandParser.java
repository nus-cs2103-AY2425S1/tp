package seedu.address.logic.parser;

import seedu.address.logic.commands.ListIncompleteCommand;

/**
 * Parses input arguments to generate a {@link ListIncompleteCommand}.
 *
 * The parser for the command that lists all incomplete tasks. This parser currently does not process
 * any specific arguments and creates a new {@link ListIncompleteCommand} regardless of the input.
 */
public class ListIncompleteCommandParser implements Parser<ListIncompleteCommand> {

    /**
     * Parses the given {@code args} which are expected to be empty in the context of
     * ListIncompleteCommand since it does not require any additional data.
     *
     * @param args the user input arguments for the command, expected to be empty.
     * @return a new {@link ListIncompleteCommand} object, which when executed will list all incomplete tasks.
     */
    @Override
    public ListIncompleteCommand parse(String args) {
        return new ListIncompleteCommand();
    }
}
