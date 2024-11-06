package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty() || args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_TAG);

        String[] nameKeywords = argMultimap.isPresent(PREFIX_NAME)
                ? argMultimap.getValue(PREFIX_NAME).get().trim().split("\\s+")
                : null;
        String[] phoneKeywords = argMultimap.isPresent(PREFIX_PHONE)
                ? argMultimap.getValue(PREFIX_PHONE).get().trim().split("\\s+")
                : null;
        String[] addressKeywords = argMultimap.isPresent(PREFIX_ADDRESS)
                ? Arrays.stream(argMultimap.getValue(PREFIX_ADDRESS).get().trim().split("_"))
                    .map(String::trim).toArray(String[]::new)
                : null;
        String[] tagKeywords = argMultimap.isPresent(PREFIX_TAG)
                ? Arrays.stream(argMultimap.getValue(PREFIX_TAG).get().trim().split("_"))
                    .map(String::trim).toArray(String[]::new)
                : null;

        // Create the specific predicates, or pass null if no valid keywords were provided
        NameContainsKeywordsPredicate namePredicate = nameKeywords != null
                ? new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)) : null;
        PhoneContainsKeywordsPredicate phonePredicate = phoneKeywords != null
                ? new PhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords)) : null;
        AddressContainsKeywordsPredicate addressPredicate = addressKeywords != null
                ? new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)) : null;
        TagContainsKeywordsPredicate tagPredicate = tagKeywords != null
                ? new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)) : null;

        return new FindCommand(namePredicate, phonePredicate, addressPredicate, tagPredicate);
    }

}
