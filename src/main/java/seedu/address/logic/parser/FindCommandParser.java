package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;

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
            String nameKeywords = argMultimap.getValue(PREFIX_NAME).get();
            predicateGroup.add(new NameContainsKeywordsPredicate(
                    ParserUtil.parseKeywords(nameKeywords, PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get();
            predicateGroup.add(new EmailContainsKeywordsPredicate(
                    ParserUtil.parseKeywords(emailKeywords, PREFIX_EMAIL)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            String genderKeywords = argMultimap.getValue(PREFIX_GENDER).get();
            predicateGroup.add(new GenderMatchesKeywordsPredicate(
                    ParserUtil.parseGenderKeywords(genderKeywords, PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            String ageKeywords = argMultimap.getValue(PREFIX_AGE).get();
            predicateGroup.add(new AgeContainsKeywordsPredicate(
                    ParserUtil.parseAgeKeywords(ageKeywords, PREFIX_AGE)));
        }
        if (argMultimap.getValue(PREFIX_STUDY_GROUP_TAG).isPresent()) {
            String tagKeywords = argMultimap.getValue(PREFIX_STUDY_GROUP_TAG).get();
            predicateGroup.add(new StudyGroupsContainKeywordsPredicate(
                    ParserUtil.parseKeywords(tagKeywords, PREFIX_STUDY_GROUP_TAG)));
        }
        if (argMultimap.getValue(PREFIX_DETAIL).isPresent()) {
            String detailKeywords = argMultimap.getValue(PREFIX_DETAIL).get();
            predicateGroup.add(new DetailContainsKeywordsPredicate(
                    ParserUtil.parseKeywords(detailKeywords, PREFIX_DETAIL)));
        }

        if (!predicateGroup.isAnyPredicateAdded()) {
            throw new ParseException(FindCommand.MESSAGE_NO_CRITERIA);
        }

        return new FindCommand(predicateGroup);
    }

}
