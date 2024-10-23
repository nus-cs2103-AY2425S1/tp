package seedu.address.logic.parser.prefix;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


/**
 * A utility class to handle the parsing of various prefixes and attributes for commands.
 */
public class PrefixHandler {

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
    private String getTypeOfAttribute(Object attribute) {
        if (attribute instanceof Phone) {
            return "PHONE";
        } else if (attribute instanceof Email) {
            return "EMAIL";
        } else if (attribute instanceof Address) {
            return "ADDRESS";
        } else if (attribute instanceof Role) {
            return "ROLE";
        } else {
            return "TAG";
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
            case "PHONE":
                Phone phoneOfPerson = person.getPhone();
                Phone phoneNumber = (Phone) attribute;
                if (phoneOfPerson.equals(phoneNumber)) {
                    matchingPersons.add(person);
                }
                break;

            case "EMAIL":
                Email emailOfPerson = person.getEmail();
                Email email = (Email) attribute;
                if (emailOfPerson.equals(email)) {
                    matchingPersons.add(person);
                }
                break;

            case "ADDRESS":
                Address addressOfPerson = person.getAddress();
                Address address = (Address) attribute;
                if (addressOfPerson.equals(address)) {
                    matchingPersons.add(person);
                }
                break;

            case "TAG":
                Set<Tag> tagsOfPerson = person.getTags();
                @SuppressWarnings("unchecked")
                Set<Tag> tagsSet = (Set<Tag>) attribute;
                if (tagsOfPerson.containsAll(tagsSet)) {
                    matchingPersons.add(person);
                }
                break;

            case "ROLE":
                Role roleOfPerson = person.getRole();
                Role role = (Role) attribute;
                if (roleOfPerson.equals(role)) {
                    matchingPersons.add(person);
                }
                break;

            default:
                break;
            }
        }
        return matchingPersons;
    }
}
