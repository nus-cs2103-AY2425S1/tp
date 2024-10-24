package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new AddNotesCommand object
 */
public class AddNotesCommandParser implements Parser<AddNotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddNotesCommand
     * and returns a AddNotesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NOTES);

        Index index;
        Note note;
        try {
            String noteName = argMultimap.getValue(PREFIX_NOTES).orElse("");
            note = ParserUtil.parseNote(noteName);

            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddNotesCommand.MESSAGE_USAGE), ive);
        }

        return new AddNotesCommand(index, note);
    }
}
