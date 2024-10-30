package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.AgeContainsKeywordsPredicate;
import seedu.address.model.person.predicates.DetailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PredicateGroup;
import seedu.address.model.person.predicates.StudyGroupsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand and returns a FindCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_GENDER,
                PREFIX_AGE, PREFIX_DETAIL, PREFIX_STUDY_GROUP_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_GENDER, PREFIX_AGE,
                PREFIX_STUDY_GROUP_TAG, PREFIX_DETAIL);

        PredicateGroup predicateGroup = new PredicateGroup();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().split("\\s+");
            predicateGroup.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            predicateGroup.add(new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            String[] genderKeywords = argMultimap.getValue(PREFIX_GENDER).get().split("\\s+");
            predicateGroup.add(new GenderMatchesKeywordsPredicate(Arrays.asList(genderKeywords)));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            String[] ageKeywords = argMultimap.getValue(PREFIX_AGE).get().split("\\s+");
            predicateGroup.add(new AgeContainsKeywordsPredicate(Arrays.asList(ageKeywords)));
        }
        if (argMultimap.getValue(PREFIX_STUDY_GROUP_TAG).isPresent()) {
            String[] tagKeywords = argMultimap.getValue(PREFIX_STUDY_GROUP_TAG).get().split("\\s+");
            predicateGroup.add(new StudyGroupsContainKeywordsPredicate(Arrays.asList(tagKeywords)));
        }
        if (argMultimap.getValue(PREFIX_DETAIL).isPresent()) {
            String[] detailKeywords = argMultimap.getValue(PREFIX_DETAIL).get().split("\\s+");
            predicateGroup.add(new DetailContainsKeywordsPredicate(Arrays.asList(detailKeywords)));
        }

        if (!predicateGroup.isAnyPredicateAdded()) {
            throw new ParseException(FindCommand.MESSAGE_NO_CRITERIA);
        }

        return new FindCommand(predicateGroup);
    }

}
