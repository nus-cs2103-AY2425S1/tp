package seedu.address.logic.parser;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Notes;

import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new AddNotesCommand object.
 */
public class AddNotesCommandParser implements Parser<AddNotesCommand> {
    /**
     * Parses the given {@code String} of arguments and returns an AddNotesCommand object for execution.
     *
     * @param args The user input arguments for the command.
     * @return An AddNotesCommand to execute.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddNotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NOTES);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE), ive);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_NOTES)){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NOTES).get().isEmpty()){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNotesCommand.MESSAGE_EMPTY_NOTE));
        }
        Notes notes = ParserUtil.parseNotes(argMultimap.getValue(PREFIX_NOTES).get());
        return new AddNotesCommand(index, notes);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
