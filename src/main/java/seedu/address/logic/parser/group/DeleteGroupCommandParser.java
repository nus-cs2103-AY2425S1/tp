package seedu.address.logic.parser.group;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.group.DeleteGroupCommand;
import seedu.address.logic.parser.Parser;


/**
 * Parser for commands of type DeleteGroupCommand. Parses the argument by trimming whitespaces and returning a new
 * {@code DeleteGroupCommand} with the trimmed argument.
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String args) {
        requireNonNull(args);
        return new DeleteGroupCommand(args.trim());
    }
}
