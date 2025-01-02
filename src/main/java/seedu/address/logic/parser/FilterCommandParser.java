package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandCommons.getErrorMessage;
import static seedu.address.logic.commands.CommandCommons.parseField;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.client.predicates.AddressContainsSubstringPredicate;
import seedu.address.model.client.predicates.CombinedPredicate;
import seedu.address.model.client.predicates.EmailContainsSubstringPredicate;
import seedu.address.model.client.predicates.IncomeComparisonPredicate;
import seedu.address.model.client.predicates.JobContainsSubstringPredicate;
import seedu.address.model.client.predicates.NameContainsSubstringPredicate;
import seedu.address.model.client.predicates.PhoneContainsSubstringPredicate;
import seedu.address.model.client.predicates.RemarkContainsSubstringPredicate;
import seedu.address.model.client.predicates.StatusStartsWithSubstringPredicate;
import seedu.address.model.client.predicates.TierStartsWithSubstringPredicate;
import seedu.address.model.util.IncomeComparisonOperator;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String TIER_NO_VALUE_PROVIDED_MESSAGE = "Tier has not been provided any value to filter by.\n"
            + "Please specify a tier label to filter by, such as 'Gold', 'Silver', 'Bronze', 'Reject', or 'NA'.\n"
            + "To filter for clients without a visible tier label beside their name, use: filter t/ NA";

    public static final String STATUS_NO_VALUE_PROVIDED_MESSAGE = "Status has not been provided any value "
            + "to filter by.\n"
            + "Please specify a status label to filter by, such as 'Urgent', 'Non_Urgent', or 'NA'.\n"
            + "To filter for clients without a visible status label beside their name, use: filter s/ NA";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_INCOME, PREFIX_JOB, PREFIX_REMARK, PREFIX_TIER, PREFIX_STATUS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_INCOME, PREFIX_JOB, PREFIX_REMARK, PREFIX_TIER, PREFIX_STATUS);

        // Throw an error if no filters are used
        long numberOfFiltersUsed = countPrefixesUsed(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_JOB, PREFIX_INCOME, PREFIX_REMARK, PREFIX_TIER, PREFIX_STATUS);

        if (numberOfFiltersUsed == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // Handle flags and search terms
        List<Predicate<Client>> predicates = collectPredicates(argMultimap);
        Predicate<Client> combinedPredicate = combinePredicates(predicates);

        return new FilterCommand(combinedPredicate);
    }

    /**
     * Counts the number of prefixes used in the given {@code ArgumentMultimap}.
     *
     * @param argMultimap The ArgumentMultimap containing the parsed arguments.
     * @param prefixes The prefixes to check in the argument map.
     * @return The number of prefixes that are present in the argument map.
     */
    private long countPrefixesUsed(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes)
                .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                .count();
    }

    /**
     * Collects the list of predicates based on the provided argument map.
     *
     * @param argMultimap The argument multimap containing the parsed arguments.
     * @return A list of predicates corresponding to the filters provided.
     * @throws ParseException if there are any parsing issues.
     */
    private List<Predicate<Client>> collectPredicates(ArgumentMultimap argMultimap) throws ParseException {
        // Collect individual predicates to be combined with AND operator later.
        // This "collection then combine" approach enhances testability by avoiding the use of anonymous
        // lambda predicates which ensures that the combined predicate can be easily tested and debugged.

        List<Predicate<Client>> predicates = new ArrayList<>();
        Set<String> errors = new LinkedHashSet<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parseName(
                    argMultimap.getValue(PREFIX_NAME).get()).fullName, errors));
            predicates.add(new NameContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parsePhone(
                            argMultimap.getValue(PREFIX_PHONE).get()).value, errors));
            predicates.add(new PhoneContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parseEmail(
                    argMultimap.getValue(PREFIX_EMAIL).get()).value, errors));
            predicates.add(new EmailContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parseAddress(
                    argMultimap.getValue(PREFIX_ADDRESS).get()).value, errors));
            predicates.add(new AddressContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parseJob(
                    argMultimap.getValue(PREFIX_JOB).get()).value, errors));
            predicates.add(new JobContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_INCOME).isPresent()) {
            String operatorAndIncome = argMultimap.getValue(PREFIX_INCOME).get();
            try {
                IncomeComparisonOperator operator =
                        ParserUtil.parseIncomeComparisonOperator(operatorAndIncome.substring(0, 1));
                BigInteger income = ParserUtil.parseIncome(operatorAndIncome.substring(1)).value;
                predicates.add(new IncomeComparisonPredicate(operator, income));
            } catch (StringIndexOutOfBoundsException e) {
                errors.add("i/ has not been provided with a comparison operator.\n"
                                + IncomeComparisonOperator.MESSAGE_CONSTRAINTS);
            } catch (ParseException e) {
                errors.add(e.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> ParserUtil.parseRemark(
                    argMultimap.getValue(PREFIX_REMARK).get()).value, errors));
            predicates.add(new RemarkContainsSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_TIER).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> argMultimap.getValue(
                    PREFIX_TIER).get(), errors));
            if (substring.isEmpty()) {
                errors.add(TIER_NO_VALUE_PROVIDED_MESSAGE);
            }
            predicates.add(new TierStartsWithSubstringPredicate(substring));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String substring = parseFieldForFilterCommand(() -> parseField(() -> argMultimap.getValue(
                    PREFIX_STATUS).get(), errors));
            if (substring.isEmpty()) {
                errors.add(STATUS_NO_VALUE_PROVIDED_MESSAGE);
            }
            predicates.add(new StatusStartsWithSubstringPredicate(substring));
        }
        if (!errors.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getErrorMessage(errors)));
        }
        return predicates;
    }

    /**
     * Combines the given list of predicates into a single predicate using the AND operator.
     * The combined predicate returns {@code true} only if all predicates return {@code true}.
     *
     * @param predicates The list of predicates to combine. Must not be null or empty.
     * @return A combined predicate that represents the logical AND of all provided predicates.
     * @throws IllegalArgumentException if the list of predicates is empty.
     */
    private Predicate<Client> combinePredicates(List<Predicate<Client>> predicates) {
        Objects.requireNonNull(predicates, "Predicates list must not be null");
        if (predicates.isEmpty()) {
            throw new IllegalArgumentException("Predicates list must not be empty");
        }

        return new CombinedPredicate(predicates);
    }

    private String parseFieldForFilterCommand(Supplier<String> otherMethodCall) {
        String possibleValue = otherMethodCall.get();
        return possibleValue == null ? "" : possibleValue;
    }
}
