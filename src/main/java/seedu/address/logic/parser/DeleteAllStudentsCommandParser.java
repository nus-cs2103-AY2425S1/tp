package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteAllStudentsCommand;

/**
 * Parses input arguments and creates a new DeleteAllStudentsCommand object
 */
public class DeleteAllStudentsCommandParser implements Parser<DeleteAllStudentsCommand> {
    @Override
    public DeleteAllStudentsCommand parse(String userInput) {
        return new DeleteAllStudentsCommand();
    }
}
