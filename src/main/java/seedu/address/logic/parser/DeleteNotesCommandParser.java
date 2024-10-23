package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteNotesCommand object
 */
public class DeleteNotesCommandParser implements Parser<DeleteNotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteNotesCommand
     * and returns a DeleteNotesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NOTES_INDEX);

        Index index;
        Index noteIndex;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

            String noteIndexName = argMultimap.getValue(PREFIX_NOTES_INDEX).orElse("");
            noteIndex = ParserUtil.parseIndex(noteIndexName);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteNotesCommand.MESSAGE_USAGE), ive);
        }

        return new DeleteNotesCommand(index, noteIndex);
    }
}
