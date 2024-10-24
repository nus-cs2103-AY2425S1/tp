package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;
import seedu.address.model.skill.SkillsContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommandParser object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommandParser
     * and returns a FilterCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_SKILL);

        if (!areAnyPrefixPresent(argMultimap, PREFIX_TAG, PREFIX_SKILL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Skill> skillSet = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILL));

        List<String> tagList = new ArrayList<String>();
        tagSet.forEach(tag -> tagList.add(tag.tagName));

        List<String> skillList = new ArrayList<String>();
        skillSet.forEach(skill -> skillList.add(skill.skill));

        return new FilterCommand(new SkillsContainsKeywordsPredicate(skillList),
                new TagsContainsKeywordsPredicate(tagList));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
