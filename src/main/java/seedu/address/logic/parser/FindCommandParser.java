package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOT_MONTHPAID;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.MonthPaidContainsKeywordsPredicate;
import seedu.address.model.person.NameAndClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NotMonthPaidContainsKeywordsPredicate;

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
        // tokenize's args expects a preamble - we do not have a preamble!
        // which is why tests fail if userInput does not start with a single whitespace
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CLASSID, PREFIX_MONTHPAID,
                PREFIX_NOT_MONTHPAID);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_CLASSID, PREFIX_MONTHPAID, PREFIX_NOT_MONTHPAID);

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            String[] classIdKeywords = argMultimap.getValue(PREFIX_CLASSID).get().split("\\s+");
            return new FindCommand(new NameAndClassIdContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                    Arrays.asList(classIdKeywords)));
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent() && argMultimap.getValue(PREFIX_CLASSID).isEmpty()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (argMultimap.getValue(PREFIX_NAME).isEmpty() && argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            String[] classIdKeywords = argMultimap.getValue(PREFIX_CLASSID).get().split("\\s+");
            return new FindCommand(new ClassIdContainsKeywordsPredicate(Arrays.asList(classIdKeywords)));
        } else if (argMultimap.getValue(PREFIX_MONTHPAID).isPresent()) {
            String[] monthPaidKeywords = argMultimap.getValue(PREFIX_MONTHPAID).get().split("\\s+");
            return new FindCommand(new MonthPaidContainsKeywordsPredicate(Arrays.asList(monthPaidKeywords)));
        } else if (argMultimap.getValue(PREFIX_NOT_MONTHPAID).isPresent()) {
            String[] notMonthPaidKeywords = argMultimap.getValue(PREFIX_NOT_MONTHPAID).get().split("\\s+");
            return new FindCommand(new NotMonthPaidContainsKeywordsPredicate(Arrays.asList(notMonthPaidKeywords)));
        }

        throw new ParseException(FindCommand.NO_SEARCH_FIELDS_PROVIDED);
    }
}
