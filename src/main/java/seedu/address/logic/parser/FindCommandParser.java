package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompoundedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.OrgContainsKeywordsPredicate;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ORGANISATION);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ORGANISATION);

        String nameArgs = argMultimap.getValue(PREFIX_NAME).orElse("");
        String orgArgs = argMultimap.getValue(PREFIX_ORGANISATION).orElse("");

        if (nameArgs.isEmpty() && orgArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = nameArgs.split("\\s+");
        String[] orgKeywords = orgArgs.split("\\s+");

        NameContainsKeywordsPredicate findByNamePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));

        OrgContainsKeywordsPredicate findByOrgPredicate = new OrgContainsKeywordsPredicate(Arrays.asList(orgKeywords));

        CompoundedPredicate combinedPredicate = new CompoundedPredicate(findByNamePredicate, findByOrgPredicate);

        return new FindCommand(combinedPredicate);
    }

}
