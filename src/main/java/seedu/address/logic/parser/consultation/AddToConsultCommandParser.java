package seedu.address.logic.parser.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consultation.AddToConsultCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;

/**
 * Parses input arguments and creates a new AddToConsultCommand object.
 */
public class AddToConsultCommandParser implements Parser<AddToConsultCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddToConsultCommand
     * and returns an AddToConsultCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddToConsultCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddToConsultCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToConsultCommand.MESSAGE_USAGE));
        }

        // Use a regular for-loop to handle exceptions
        List<Name> studentNames = new ArrayList<>();
        for (String nameString : argMultimap.getAllValues(PREFIX_NAME)) {
            Name name;
            try {
                name = ParserUtil.parseName(nameString);
            } catch (ParseException e) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS, e); // Handle ParseException directly
            }
            studentNames.add(name);
        }

        return new AddToConsultCommand(index, studentNames);
    }
}
