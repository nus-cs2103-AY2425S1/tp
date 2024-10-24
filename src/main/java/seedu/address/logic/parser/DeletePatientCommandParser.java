package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.InvalidIdException;
import seedu.address.logic.commands.DeletePatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeletePatientCommand object
 */
public class DeletePatientCommandParser implements Parser<DeletePatientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeletePatientCommand
     * and returns a DeletePatientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeletePatientCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_ID)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePatientCommand.MESSAGE_USAGE));
        }
        int patientId;
        try {
            patientId = ParserUtil.parsePersonId(argumentMultimap.getAllValues(PREFIX_ID).get(0));
        } catch (InvalidIdException e) {
            throw new ParseException(MESSAGE_INVALID_ID, e);
        }
        return new DeletePatientCommand(patientId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
