package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.commands.EditCommand;
import seedu.address.logic.commands.contact.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonIsRolePredicate;
import seedu.address.model.role.Role;
import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;

public class SearchModeSearchCommandParser implements Parser<SearchModeSearchCommand> {
    @Override
    public SearchModeSearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM, PREFIX_ROLE);
        //for each prefix, if present, parse the value and create a predicate...
        // combine the predicates using AND
        // return the search command with the predicate
        // original predicate just show all
        Predicate<Person> predicate = Model.PREDICATE_SHOW_ALL_PERSONS;
        // if a field is present, AND with the predicate for that field
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String name = argMultimap.getValue(PREFIX_NAME).get();
            Predicate<Person> namePred = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(name),
                    Person::getName);
            predicate = predicate.and(namePred);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phone = argMultimap.getValue(PREFIX_PHONE).get();
            Predicate<Person> phonePred = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(phone),
                    Person::getPhone);
            predicate = predicate.and(phonePred);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String email = argMultimap.getValue(PREFIX_EMAIL).get();
            Predicate<Person> emailPred = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(email),
                    Person::getEmail);
            predicate = predicate.and(emailPred);
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String address = argMultimap.getValue(PREFIX_ADDRESS).get();
            Predicate<Person> addressPred = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(address),
                    Person::getAddress);
            predicate = predicate.and(addressPred);
        }
        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            String telegram = argMultimap.getValue(PREFIX_TELEGRAM).get();
            Predicate<Person> telegramPred = new FieldContainsKeywordsPredicate<>(
                    Collections.singletonList(telegram),
                    Person::getTelegramUsername);
            predicate = predicate.and(telegramPred);
        }
        //role have to use separate predicate
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String roles = argMultimap.getValue(PREFIX_ROLE).get();
            // map each word in String roles to a Role object
            List<Role> roleList = Arrays.stream(roles.split("\\s+"))
                    .filter(RoleHandler::isValidRoleName) // Filter valid roles
                    .map(role -> {
                        try {
                            return RoleHandler.getRole(role);
                        } catch (InvalidRoleException e) {
                            throw new RuntimeException("Invalid role: " + role, e);
                        }
                    })
                    .collect(Collectors.toList());
            Predicate<Person> rolePred = new PersonIsRolePredicate(roleList);
            predicate = predicate.and(rolePred);
        }
        return new SearchModeSearchCommand(predicate);
    }


}
