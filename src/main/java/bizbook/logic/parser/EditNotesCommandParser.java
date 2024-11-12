package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.parser.CliSyntax.PREFIX_NOTES;
import static bizbook.logic.parser.CliSyntax.PREFIX_NOTES_INDEX;
import static java.util.Objects.requireNonNull;

import bizbook.commons.core.index.Index;
import bizbook.commons.exceptions.IllegalValueException;
import bizbook.logic.commands.EditNoteCommand;
import bizbook.logic.parser.exceptions.ParseException;
import bizbook.model.person.Note;

/**
 * Parses input arguments and returns a new EditNotesCommand object
 */
public class EditNotesCommandParser implements Parser<EditNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditNotesCommand
     * and returns a EditNotesCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditNoteCommand parse(String args) throws ParseException {
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
                    EditNoteCommand.MESSAGE_USAGE), ive);
        }

        return new EditNoteCommand(personIndex, noteIndex, note);
    }
}
