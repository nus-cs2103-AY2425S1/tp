package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.criteria.EmailSearchCriteria;
import seedu.address.logic.parser.criteria.NameSearchCriteria;
import seedu.address.logic.parser.criteria.NricSearchCriteria;
import seedu.address.logic.parser.criteria.PhoneSearchCriteria;
import seedu.address.logic.parser.criteria.RoleSearchCriteria;
import seedu.address.logic.parser.criteria.SearchCriteria;
import seedu.address.logic.parser.criteria.TagSearchCriteria;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
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
        List<SearchCriteria> criteria = new ArrayList<>();
        ArgumentMultimap keywords = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_NRIC, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROLE, PREFIX_TAG);
        // Needs to be less than 2 because the argument tokenized will always produce
        // a key-value pair between Prefix("") and the preamble (the values before the first valid prefix)
        if (keywords.getPrefixes().size() < 2) {
            throw new ParseException("Please enter at least one keyword!\n"
            + FindCommand.MESSAGE_USAGE);
        }

        if (!keywords.getPreamble().equals("")) {
            throw new ParseException("Please do not enter anything before the keywords!\n"
            + "Please remove this from your input: " + keywords.getPreamble());
        }

        // Checks if all the arguments after the respective prefixes are valid
        checkKeywords(keywords);

        if (keywords.getValue(PREFIX_NAME).isPresent()) {
            criteria.add(new NameSearchCriteria(keywords.getAllValues(PREFIX_NAME)));
        }
        if (keywords.getValue(PREFIX_NRIC).isPresent()) {
            criteria.add(new NricSearchCriteria(keywords.getAllValues(PREFIX_NRIC)));
        }
        if (keywords.getValue(PREFIX_ROLE).isPresent()) {
            criteria.add(new RoleSearchCriteria(keywords.getAllValues(PREFIX_ROLE)));
        }
        if (keywords.getValue(PREFIX_TAG).isPresent()) {
            criteria.add(new TagSearchCriteria(keywords.getAllValues(PREFIX_TAG)));
        }
        if (keywords.getValue(PREFIX_PHONE).isPresent()) {
            criteria.add(new PhoneSearchCriteria(keywords.getAllValues(PREFIX_PHONE)));
        }
        if (keywords.getValue(PREFIX_EMAIL).isPresent()) {
            criteria.add(new EmailSearchCriteria(keywords.getAllValues(PREFIX_EMAIL)));
        }

        if (criteria.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new ContainsKeywordsPredicate(criteria));
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
        List<String> phones = keywords.getAllValues(PREFIX_PHONE);
        List<String> emails = keywords.getAllValues(PREFIX_EMAIL);
        checkNames(names);
        checkNrics(nrics);
        checkRoles(roles);
        checkTags(tags);
        checkPhones(phones);
        checkEmails(emails);
    }

    private void checkEmails(List<String> emails) throws ParseException {
        boolean isAllValid = emails.stream().allMatch(Email::isValidEmail);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid Email Address!\n"
                    + Email.MESSAGE_CONSTRAINTS);
        }
    }

    private void checkPhones(List<String> phones) throws ParseException {
        boolean isAllValid = phones.stream().allMatch(Phone::isValidPhone);
        if (!isAllValid) {
            throw new ParseException("You have entered an invalid Phone Number!\n" + Phone.MESSAGE_CONSTRAINTS);
        }
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
