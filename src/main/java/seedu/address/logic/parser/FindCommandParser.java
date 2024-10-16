package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ID, PREFIX_WARD, PREFIX_DIAGNOSIS,
                        PREFIX_MEDICATION);

        if (argMultimap.numberOfUniquePrefixes() != 1 || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ID, PREFIX_WARD, PREFIX_DIAGNOSIS,
                PREFIX_MEDICATION);

        FieldContainsKeywordsPredicate predicate = null;
        String[] keywords;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicate = new FieldContainsKeywordsPredicate(Arrays.asList(keywords), "name");
        } else if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_ID).get().split("\\s+");
            predicate = new FieldContainsKeywordsPredicate(Arrays.asList(keywords), "id");
        } else if (argMultimap.getValue(PREFIX_WARD).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_WARD).get().split("\\s+");
            predicate = new FieldContainsKeywordsPredicate(Arrays.asList(keywords), "ward");
        } else if (argMultimap.getValue(PREFIX_DIAGNOSIS).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_DIAGNOSIS).get().split("\\s+");
            predicate = new FieldContainsKeywordsPredicate(Arrays.asList(keywords), "diagnosis");
        } else if (argMultimap.getValue(PREFIX_MEDICATION).isPresent()) {
            keywords = argMultimap.getValue(PREFIX_MEDICATION).get().split("\\s+");
            predicate = new FieldContainsKeywordsPredicate(Arrays.asList(keywords), "medication");
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

}
