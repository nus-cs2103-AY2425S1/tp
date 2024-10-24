package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.info("Invalid command format. Empty arguments.");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap keywords = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_ROLE, PREFIX_TAG);
        // Needs to be less than 2 because the argument tokenized will always produce
        // a key-value pair between Prefix("") and the preamble (the values before the first valid prefix)
        if (keywords.getPrefixes().size() < 2 || !keywords.getPreamble().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        checkKeywords(keywords);

        return new FindCommand(new ContainsKeywordsPredicate(keywords));
    }

    /**
     * Checks if all the arguments after the respective prefixes are valid
     * @param keywords
     * @throws ParseException
     */
    public void checkKeywords(ArgumentMultimap keywords) throws ParseException {
        List<String> names = keywords.getAllValues(PREFIX_NAME);
        List<String> nrics = keywords.getAllValues(PREFIX_NRIC);
        List<String> roles = keywords.getAllValues(PREFIX_ROLE);
        List<String> tags = keywords.getAllValues(PREFIX_TAG);
        checkNames(names);
        checkNrics(nrics);
        checkRoles(roles);
        checkTags(tags);
    }

    private void checkTags(List<String> tags) throws ParseException {
        boolean isAllValid = tags.stream().allMatch(Tag::isValidTagName);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid Tag!\n" + Tag.MESSAGE_CONSTRAINTS);
        }
    }

    private void checkRoles(List<String> roles) throws ParseException {
        boolean isAllValid = roles.stream().allMatch(Role::isValidRole);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid Role!\n" + Role.MESSAGE_CONSTRAINTS);
        }
    }

    private void checkNrics(List<String> nrics) throws ParseException {
        boolean isAllValid = nrics.stream().allMatch(Nric::isValidNric);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid Nric!\n" + Nric.MESSAGE_CONSTRAINTS);
        }
    }

    private void checkNames(List<String> names) throws ParseException {
        boolean isAllValid = names.stream().allMatch(Name::isValidName);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid name!\n" + Name.MESSAGE_CONSTRAINTS);
        }
    }
}
