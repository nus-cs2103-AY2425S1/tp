package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import seedu.address.logic.commands.NotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;

/**
 * Parses input arguments and creates a new NotesCommand object
 */
public class NotesCommandParser implements Parser<NotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotesCommand
     * and returns a NotesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VIEW, PREFIX_DELETE, PREFIX_ADD, PREFIX_NOTES);

        try {
            // Check if exactly one command prefix is present
            int commandPrefixes = 0;
            if (isPrefixPresent(argMultimap, PREFIX_VIEW)) {
                commandPrefixes++;
            }
            if (isPrefixPresent(argMultimap, PREFIX_DELETE)) {
                commandPrefixes++;
            }
            if (isPrefixPresent(argMultimap, PREFIX_ADD)) {
                commandPrefixes++;
            }

            if (commandPrefixes != 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        NotesCommand.MESSAGE_USAGE));
            }

            Name name;
            if (isPrefixPresent(argMultimap, PREFIX_VIEW)) {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_VIEW).get());
                return new NotesCommand(name, NotesCommand.Mode.VIEW);
            }

            if (isPrefixPresent(argMultimap, PREFIX_DELETE)) {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_DELETE).get());
                return new NotesCommand(name, NotesCommand.Mode.DELETE);
            }

            // Must be add command at this point
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_ADD).get());

            if (!isPrefixPresent(argMultimap, PREFIX_NOTES)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        NotesCommand.MESSAGE_USAGE));
            }

            Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).get());
            return new NotesCommand(name, NotesCommand.Mode.ADD, notes);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE), pe);
        }
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
