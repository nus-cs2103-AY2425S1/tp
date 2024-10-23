package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseAddressPredicate;
import static seedu.address.logic.parser.ParserUtil.parseCompanyPredicate;
import static seedu.address.logic.parser.ParserUtil.parseEmailPredicate;
import static seedu.address.logic.parser.ParserUtil.parseNamePredicate;
import static seedu.address.logic.parser.ParserUtil.parsePhonePredicate;
import static seedu.address.logic.parser.ParserUtil.parseRsvpPredicate;
import static seedu.address.logic.parser.ParserUtil.parseTagPredicate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_RSVP, PREFIX_COMPANY);

        if (!isOnlyOneDistinctPrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_RSVP, PREFIX_COMPANY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_RSVP,
                PREFIX_COMPANY);

        return new FindCommand(getPredicate(argMultimap));
    }

    /**
     * Constructs a {@code Predicate} based on the provided {@code ArgumentMultimap}.
     *
     * @param argMultimap the {@code ArgumentMultimap} containing the arguments to be parsed
     * @return a {@code Predicate<Person>} that filters {@code Person} objects based on the specified arguments
     * @throws ParseException if none of the expected prefixes are present
     */
    private static Predicate<Person> getPredicate(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> address = argMultimap.getValue(PREFIX_ADDRESS);
        Optional<String> rsvp = argMultimap.getValue(PREFIX_RSVP);
        Optional<String> company = argMultimap.getValue(PREFIX_COMPANY);
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);

        if (name.isPresent()) {
            return parseNamePredicate(name.get());
        } else if (phone.isPresent()) {
            return parsePhonePredicate(phone.get());
        } else if (email.isPresent()) {
            return parseEmailPredicate(email.get());
        } else if (address.isPresent()) {
            return parseAddressPredicate(address.get());
        } else if (rsvp.isPresent()) {
            return parseRsvpPredicate(rsvp.get());
        } else if (company.isPresent()) {
            return parseCompanyPredicate(company.get());
        } else if (!tags.isEmpty()) {
            return parseTagPredicate(tags);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if exactly one distinct prefix is present in the given {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} containing the argument prefixes and values.
     * @param prefixes         Varargs of {@code Prefix} to check for presence in the {@code argumentMultimap}.
     * @return true if exactly one distinct prefix is present, false otherwise.
     */
    private static boolean isOnlyOneDistinctPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        long numberOfDistinctPrefixes = Stream.of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .distinct()
                .count();
        return numberOfDistinctPrefixes == 1;
    }
}
