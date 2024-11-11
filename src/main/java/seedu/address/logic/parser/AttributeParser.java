package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


/**
 * A utility class to handle the parsing of various prefixes and attributes for commands.
 */
public class AttributeParser {

    /**
     * Identifies the type of argument based on the prefixes present in the provided {@code ArgumentMultimap}.
     * If multiple prefixes are present, the order of checks determines which type is selected.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the parsed user input.
     * @return A string representing the type of argument (e.g., "NAME", "PHONE", "EMAIL", etc.).
     */
    public String getArgumentType(ArgumentMultimap argMultimap) {
        if (!argMultimap.getPreamble().isEmpty()) {
            return "INDEX";
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return "NAME";
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            return "ROLE";
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            return "PHONE";
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            return "EMAIL";
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            return "ADDRESS";
        }
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            return "TAG";
        }
        return "DEFAULT";
    }

    /**
     * Determines the type of the given attribute object.
     *
     * @param attribute The attribute object.
     * @return The type of the attribute as a string (e.g., "PHONE", "EMAIL", "ADDRESS").
     */
    public String getTypeOfAttribute(Object attribute) {
        if (attribute instanceof Name) {
            return "NAME";
        } else if (attribute instanceof Phone) {
            return "PHONE";
        } else if (attribute instanceof Email) {
            return "EMAIL";
        } else if (attribute instanceof Address) {
            return "ADDRESS";
        } else if (attribute instanceof Role) {
            return "ROLE";
        } else if (attribute instanceof Set<?>) {
            return "TAG";
        } else {
            return "ERROR";
        }
    }

    /**
     * Finds all persons in the given list whose attributes match the specified attribute.
     * The matching is based on the type of attribute (e.g., phone number, email, address).
     *
     * @param lastShownList The list of persons to search through.
     * @param attribute The attribute to match against (e.g., a phone number, email, or address).
     * @return A list of persons whose attributes match the provided attribute.
     */
    public List<Person> findPersonByAttribute(List<Person> lastShownList, Object attribute) {
        String type = getTypeOfAttribute(attribute);
        List<Person> matchingPersons = new ArrayList<>();
        for (Person person : lastShownList) {
            switch (type) {
            case "NAME":
                findMatchingPersons(person, person.getName(), attribute, matchingPersons);
                break;
            case "PHONE":
                findMatchingPersons(person, person.getPhone(), attribute, matchingPersons);
                break;
            case "EMAIL":
                findMatchingPersons(person, person.getEmail(), attribute, matchingPersons);
                break;
            case "ADDRESS":
                findMatchingPersons(person, person.getAddress(), attribute, matchingPersons);
                break;
            case "TAG":
                findMatchByTags(person, attribute, matchingPersons);
                break;
            case "ROLE":
                findMatchingPersons(person, person.getRole(), attribute, matchingPersons);
                break;
            default:
                break;
            }
        }
        return matchingPersons;
    }

    /**
     * Finds and adds a matching person to the list based on a specific attribute.
     *
     * @param person The person to be checked for matching.
     * @param otherAttribute The person's attribute to compare against.
     * @param attribute The attribute value to match.
     * @param matchingPersons The list of matching persons to which a match will be added.
     */
    public void findMatchingPersons(Person person, Object otherAttribute, Object attribute,
                                  List<Person> matchingPersons) {
        if (attribute.equals(otherAttribute)) {
            matchingPersons.add(person);
        }
    }

    /**
     * Finds and adds a person to the list if their tags match the specified tags.
     *
     * @param person The person to be checked for matching tags.
     * @param attribute The attribute containing the set of tags to match.
     * @param matchingPersons The list of matching persons to which a match will be added.
     */
    public void findMatchByTags(Person person, Object attribute,
                                List<Person> matchingPersons) {
        Set<Tag> tagsOfPerson = person.getTags();
        @SuppressWarnings("unchecked")
        Set<Tag> tagsSet = (Set<Tag>) attribute;
        if (tagsOfPerson.containsAll(tagsSet)) {
            matchingPersons.add(person);
        }

    }

    /**
     * Extracts a string value from the specified prefix in the given argument multimap.
     *
     * @param prefix The prefix indicating the desired value.
     * @param argumentMultimap The argument multimap containing the value.
     * @return The string value associated with the prefix.
     */
    public String parseString(Prefix prefix, ArgumentMultimap argumentMultimap) {
        Optional<String> optionalAttribute = argumentMultimap.getValue(prefix);
        return optionalAttribute.get();
    }

    /**
     * Parses the phone number from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the phone number.
     * @return A DeleteCommand to delete the person by phone number.
     * @throws ParseException if the phone number format is invalid.
     */
    public Phone parsePhone(ArgumentMultimap argumentMultimap)
            throws ParseException {
        String phoneString = parseString(PREFIX_PHONE, argumentMultimap);
        return ParserUtil.parsePhone(phoneString);
    }

    /**
     * Parses the index from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the index.
     * @return A DeleteCommand to delete the person by index.
     * @throws ParseException if the index format is invalid.
     */
    public Index parseIndex(ArgumentMultimap argumentMultimap)
            throws ParseException {
        String stringIndex = argumentMultimap.getPreamble();
        return ParserUtil.parseIndex(stringIndex);
    }

    /**
     * Parses the name predicate from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the name.
     * @return A DeleteCommand to delete the person by name.
     */
    public NameContainsKeywordsPredicate parseName(ArgumentMultimap argumentMultimap) {
        String predicateString = parseString(PREFIX_NAME, argumentMultimap);
        return new NameContainsKeywordsPredicate(predicateString);

    }

    /**
     * Parses the address from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the address.
     * @return A DeleteCommand to delete the person by address.
     * @throws ParseException if the address format is invalid.
     */
    public Address parseAddress(ArgumentMultimap argumentMultimap)
            throws ParseException {
        String stringAddress = parseString(PREFIX_ADDRESS, argumentMultimap);
        return ParserUtil.parseAddress(stringAddress);
    }

    /**
     * Parses the email from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the email.
     * @return A DeleteCommand to delete the person by email.
     * @throws ParseException if the email format is invalid.
     */
    public Email parseEmail(ArgumentMultimap argumentMultimap)
            throws ParseException {
        String stringEmail = parseString(PREFIX_EMAIL, argumentMultimap);
        return ParserUtil.parseEmail(stringEmail);
    }


    /**
     * Parses the role from the given argument multimap.
     *
     * @param argumentMultimap The argument multimap containing the role.
     * @return A Role object representing the parsed role.
     * @throws ParseException if the role format is invalid.
     */
    public Role parseRole(ArgumentMultimap argumentMultimap)
            throws ParseException {
        String stringRole = parseString(PREFIX_ROLE, argumentMultimap);
        return ParserUtil.parseRole(stringRole);
    }


    /**
     * Parses the tags from the given argument multimap and returns a DeleteCommand.
     *
     * @param argumentMultimap The argument multimap containing the tags.
     * @return A DeleteCommand to delete the person by tags.
     * @throws ParseException if the tag format is invalid.
     */
    public Set<Tag> parseTags(ArgumentMultimap argumentMultimap)
            throws ParseException {
        List<String> stringTags = argumentMultimap.getAllValues(PREFIX_TAG);
        return ParserUtil.parseTags(stringTags);
    }


}
