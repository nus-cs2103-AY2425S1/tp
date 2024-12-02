package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        List<Prefix> sortCriteria = new ArrayList<>();
        List<Prefix> allowedCriteria = new ArrayList<>(Arrays.asList(
                PREFIX_JOBCODE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG));

        if (args.isEmpty()) {
            return new SortCommand(sortCriteria);
        }
        // Tokenize the arguments using ArgumentTokenizer
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_JOBCODE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_JOBCODE, PREFIX_TAG);

        for (Prefix prefix : allowedCriteria) {
            if (argMultimap.getValue(prefix).isPresent() && !argMultimap.getValue(prefix).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        sortCriteria = ArgumentTokenizer.extractPrefixes(args,
                PREFIX_JOBCODE, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        // Create and return the SortCommand object with the criteria in order
        return new SortCommand(sortCriteria);
    }
}
