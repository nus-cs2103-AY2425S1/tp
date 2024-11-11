package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMeetsCriteriaPredicate;
/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @param args The user input arguments.
     * @return A FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_INCOME, PREFIX_AGE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_INCOME, PREFIX_AGE, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS, PREFIX_INCOME, PREFIX_AGE);

        List<String> phoneCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            phoneCriteria = ParserUtil.parsePhoneCriteria(argMultimap.getValue(PREFIX_PHONE).get().trim());
        }
        List<String> emailCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            emailCriteria = ParserUtil.parseCriteria(argMultimap.getValue(PREFIX_EMAIL).get().trim());
        }

        List<String> addressCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            addressCriteria = ParserUtil.parseCriteria(argMultimap.getValue(PREFIX_ADDRESS).get().trim());
        }

        List<String> incomeCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_INCOME)) {
            incomeCriteria = ParserUtil.parseIncomeCriteria(argMultimap.getValue(PREFIX_INCOME).get().trim());
        }

        List<String> ageCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_AGE)) {
            ageCriteria = ParserUtil.parseAgeCriteria(argMultimap.getValue(PREFIX_AGE).get().trim());
        }

        List<String> tagsCriteria = new ArrayList<>();
        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            tagsCriteria = ParserUtil.parseCriteria(argMultimap.getValue(PREFIX_TAG).get().trim());
        }

        return new FilterCommand(
            new PersonMeetsCriteriaPredicate(phoneCriteria, emailCriteria,
                addressCriteria, incomeCriteria, ageCriteria, tagsCriteria));
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The ArgumentMultimap containing the prefixes.
     * @param prefixes The prefixes to check.
     * @return True if any of the prefixes contain non-empty values, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
