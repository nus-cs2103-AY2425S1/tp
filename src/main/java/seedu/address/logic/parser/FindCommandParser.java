package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOT_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonPredicateBuilder;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * Accepts n/ for name and c/ for classId.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CLASSID, PREFIX_MONTHPAID,
                PREFIX_NOT_MONTHPAID, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CLASSID,
                PREFIX_MONTHPAID, PREFIX_NOT_MONTHPAID, PREFIX_TAG);

        PersonPredicateBuilder personPredicateBuilder = new PersonPredicateBuilder();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            if (nameKeywords[0].isEmpty()) {
                throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
            }
            personPredicateBuilder = personPredicateBuilder.withNameKeywords(Arrays.asList(nameKeywords));
        }

        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            String[] classIdKeywords = argMultimap.getValue(PREFIX_CLASSID).get().split("\\s+");
            if (classIdKeywords[0].isEmpty()) {
                throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
            }
            personPredicateBuilder = personPredicateBuilder.withClassIdKeywords(Arrays.asList(classIdKeywords));
        }

        if (argMultimap.getValue(PREFIX_MONTHPAID).isPresent()) {
            String[] monthPaidKeywords = argMultimap.getValue(PREFIX_MONTHPAID).get().split("\\s+");
            if (monthPaidKeywords[0].isEmpty()) {
                throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
            }
            personPredicateBuilder = personPredicateBuilder.withMonthPaidKeywords(Arrays.asList(monthPaidKeywords));
        }

        if (argMultimap.getValue(PREFIX_NOT_MONTHPAID).isPresent()) {
            String[] notMonthPaidKeywords = argMultimap.getValue(PREFIX_NOT_MONTHPAID).get().split("\\s+");
            if (notMonthPaidKeywords[0].isEmpty()) {
                throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
            }
            personPredicateBuilder = personPredicateBuilder
                    .withNotMonthPaidKeywords(Arrays.asList(notMonthPaidKeywords));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            if (tagKeywords[0].isEmpty()) {
                throw new ParseException(FindCommand.EMPTY_SEARCH_VALUE_PROVIDED);
            }
            personPredicateBuilder = personPredicateBuilder
                    .withTagsKeywords(Arrays.asList(tagKeywords));

        }

        if (personPredicateBuilder.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.NO_SEARCH_FIELDS_PROVIDED));
        }

        return new FindCommand(personPredicateBuilder);
    }
}
