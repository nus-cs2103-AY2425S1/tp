package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MeetingContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MeetingContactsCommand object
 */
public class MeetingContactsCommandParser implements Parser<MeetingContactsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MeetingContactsCommand
     * @throws ParseException if the user input does not conform the expected
     */
    public MeetingContactsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        if (args.isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MeetingContactsCommand.MESSAGE_USAGE));
        }
        try {
            return new MeetingContactsCommand(Index
                    .fromOneBased(Integer.valueOf(argMultimap.getPreamble())));
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MeetingContactsCommand.MESSAGE_USAGE));
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS,
                    MeetingContactsCommand.MESSAGE_USAGE));
        }
    }
}
