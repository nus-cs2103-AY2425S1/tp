package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;
import static seedu.address.logic.parser.ParserUtil.parseNote;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NoteCommand}
     * and returns a {@code NoteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NOTE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_NOTE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String note = argMultimap.getValue(PREFIX_NOTE).orElse("");

        return new NoteCommand(name, parseNote(note));
    }
}
