package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and returns a new EditNotesCommand object
 */
public class EditNotesCommandParser implements Parser<EditNotesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditNotesCommand
     * and returns a EditNotesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NOTES_INDEX, PREFIX_NOTES);

        Index personIndex;
        Index noteIndex;
        Note note;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());

            String noteIndexName = argMultimap.getValue(PREFIX_NOTES_INDEX).orElse("");
            noteIndex = ParserUtil.parseIndex(noteIndexName);
            String noteName = argMultimap.getValue(PREFIX_NOTES).orElse("");
            note = ParserUtil.parseNote(noteName);

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditNotesCommand.MESSAGE_USAGE), ive);
        }

        return new EditNotesCommand(personIndex, noteIndex, note);
    }
}
