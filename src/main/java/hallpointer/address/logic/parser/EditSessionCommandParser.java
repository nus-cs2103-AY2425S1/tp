package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_DATE;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.EditSessionCommand;
import hallpointer.address.logic.commands.EditSessionCommand.EditSessionDescriptor;
import hallpointer.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditSessionCommand object
 */
public class EditSessionCommandParser implements Parser<EditSessionCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditSessionCommand
     * and returns an EditSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SESSION_NAME, PREFIX_DATE, PREFIX_POINTS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSessionCommand.MESSAGE_USAGE), pe);
        }

        EditSessionDescriptor editSessionDescriptor = new EditSessionDescriptor();
        if (argMultimap.getValue(PREFIX_SESSION_NAME).isPresent()) {
            editSessionDescriptor.setName(ParserUtil.parseSessionName(argMultimap.getValue(PREFIX_SESSION_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editSessionDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_POINTS).isPresent()) {
            editSessionDescriptor.setPoints(ParserUtil.parsePoints(argMultimap.getValue(PREFIX_POINTS).get()));
        }

        if (!editSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditSessionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditSessionCommand(index, editSessionDescriptor);
    }

}
