package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import seedu.address.commons.core.index.Index;
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

            String identifier;
            if (isPrefixPresent(argMultimap, PREFIX_VIEW)) {
                identifier = argMultimap.getValue(PREFIX_VIEW).get();
                return createCommand(identifier, NotesCommand.Mode.VIEW, null);
            }

            if (isPrefixPresent(argMultimap, PREFIX_DELETE)) {
                identifier = argMultimap.getValue(PREFIX_DELETE).get();
                return createCommand(identifier, NotesCommand.Mode.DELETE, null);
            }

            // Must be add command at this point
            identifier = argMultimap.getValue(PREFIX_ADD).get();

            if (!isPrefixPresent(argMultimap, PREFIX_NOTES)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        NotesCommand.MESSAGE_USAGE));
            }

            Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).get());
            return createCommand(identifier, NotesCommand.Mode.ADD, notes);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a NotesCommand based on the identifier type (index or name).
     */
    private NotesCommand createCommand(String identifier, NotesCommand.Mode mode, Notes notes) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(identifier);
            return notes == null ? new NotesCommand(index, mode) : new NotesCommand(index, mode, notes);
        } catch (ParseException pe) {
            // If not an index, treat as name
            Name name = ParserUtil.parseName(identifier);
            return notes == null ? new NotesCommand(name, mode) : new NotesCommand(name, mode, notes);
        }
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }
}
