package seedu.address.logic.parser.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consultation.RemoveFromConsultCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new RemoveFromConsultCommand object
 */
public class RemoveFromConsultCommandParser implements Parser<RemoveFromConsultCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveFromConsultCommand
     * and returns a RemoveFromConsultCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public RemoveFromConsultCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            // Parse index
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveFromConsultCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveFromConsultCommand.MESSAGE_USAGE));
        }

        // Use a regular for-loop to parse names and handle exceptions
        List<Name> studentNames = new ArrayList<>();
        for (String nameString : argMultimap.getAllValues(PREFIX_NAME)) {
            try {
                Name name = ParserUtil.parseName(nameString);
                studentNames.add(name);
            } catch (ParseException e) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS, e); // Handle ParseException directly
            }
        }

        return new RemoveFromConsultCommand(index, studentNames);
    }
}
