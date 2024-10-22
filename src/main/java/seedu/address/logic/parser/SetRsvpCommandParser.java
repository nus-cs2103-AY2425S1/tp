package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetRsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.RsvpStatus;

/**
 * Parses input arguments and creates a new SetRsvpCommand object
 */
public class SetRsvpCommandParser implements Parser<SetRsvpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetRsvpCommand.
     * Expected format: "set {index} {action}" where action is 1 (Coming), 2 (Not Coming), or 3 (Pending).
     */
    public SetRsvpCommand parse(String args) throws ParseException {
        try {
            String[] parts = args.trim().split("\\s+");

            if (parts.length != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));
            }

            // Parse the index (first part)
            Index index = ParserUtil.parseIndex(parts[0]);

            // Parse the action (second part)
            int action = Integer.parseInt(parts[1]);
            RsvpStatus rsvpStatus;

            // Return the new command
            return new SetRsvpCommand(index, action);

        } catch (ParseException | NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE), e);
        }
    }
}
