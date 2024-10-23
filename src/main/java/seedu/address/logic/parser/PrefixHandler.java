package seedu.address.logic.parser;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class PrefixHandler {
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
     * Determines the type of the attribute.
     *
     * @param attribute The attribute object.
     * @return The type of the attribute as a string.
     */
    private String getTypeOfAttribute(Object attribute) {
        if (attribute instanceof Phone) {
            return "PHONE";
        } else if (attribute instanceof Email) {
            return "EMAIL";
        } else if (attribute instanceof Address) {
            return "ADDRESS";
        } else if (attribute instanceof Role){
            return "ROLE";
        } else {
            return "TAG";
        }
    }

    public List<Person> findPersonByAttribute(List<Person> lastShownList, Object attribute) {
        String type = getTypeOfAttribute(attribute);
        List<Person> matchingPersons = new ArrayList<>();
        for (Person person : lastShownList) {
            switch(type) {
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
