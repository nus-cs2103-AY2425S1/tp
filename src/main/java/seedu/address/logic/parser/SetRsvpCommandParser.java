package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetRsvpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            validateArgumentLength(parts);
            Index index = validateIndex(parts);
            int rsvpInput = validateRsvpInput(parts);

            // Return the new command
            return new SetRsvpCommand(index, rsvpInput);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Validate whether index provided is a number and in range.
     */
    private Index validateIndex(String[] parts) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(parts[0]);
            if (index.getZeroBased() < 0) {
                throw new ParseException(SetRsvpCommand.MESSAGE_INVALID_INDEX);
            }
            return index;
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Validate whether argument length is correct
     */
    private void validateArgumentLength(String[] parts) throws ParseException {
        int validArgLength = 2;
        if (parts.length != validArgLength) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validate whether Rsvp input is correct
     */
    private int validateRsvpInput(String[] parts) throws ParseException {
        // Ensure the second part starts with "s/"
        if (!parts[1].startsWith("s/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRsvpCommand.MESSAGE_USAGE));
        }

        // Parse the action (second part)
        String actionString = parts[1].substring(2);
        int action;
        try {
            action = Integer.parseInt(actionString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid RSVP action format: must be a number.");
        }
        return action;
    }
}

