package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindPotentialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PredicateContainer;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_DEPARTMENT, PREFIX_ROLE);

        String typeOfPerson = argMultimap.getPreamble().trim();
        if (typeOfPerson.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        PredicateContainer predicateContainer = new PredicateContainer();
        if (argMultimap.getValue(PREFIX_NAME).isEmpty() && argMultimap.getValue(PREFIX_PHONE).isEmpty()
                && argMultimap.getValue(PREFIX_EMAIL).isEmpty() && argMultimap.getValue(PREFIX_DEPARTMENT).isEmpty()
                && argMultimap.getValue(PREFIX_ROLE).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = Arrays.stream(
                    argMultimap.getAllValues(PREFIX_NAME).get(0).split("\\s+")).toList();
            NameContainsKeywordsPredicate nameContainsKeywordsPredicate =
                    new NameContainsKeywordsPredicate(nameKeywords);
            predicateContainer.addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate);
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneKeywords = Arrays.stream(
                    argMultimap.getAllValues(PREFIX_PHONE).get(0).split("\\s+")).toList();
            PhoneContainsKeywordsPredicate phoneContainsKeywordsPredicate =
                    new PhoneContainsKeywordsPredicate(phoneKeywords);
            predicateContainer.addPhoneContainsKeywordsPredicate(phoneContainsKeywordsPredicate);
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailKeywords = Arrays.stream(
                    argMultimap.getAllValues(PREFIX_EMAIL).get(0).split("\\s+")).toList();
            EmailContainsKeywordsPredicate emailContainsKeywordsPredicate =
                    new EmailContainsKeywordsPredicate(emailKeywords);
            predicateContainer.addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate);
        }
        if (argMultimap.getValue(PREFIX_DEPARTMENT).isPresent()) {
            List<String> departmentKeywords = Arrays.stream(
                    argMultimap.getAllValues(PREFIX_DEPARTMENT).get(0).split("\\s+")).toList();
            DepartmentContainsKeywordsPredicate departmentContainsKeywordsPredicate =
                    new DepartmentContainsKeywordsPredicate(departmentKeywords);
            predicateContainer.addDepartmentContainsKeywordsPredicate(departmentContainsKeywordsPredicate);
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            List<String> roleKeywords = Arrays.stream(
                    argMultimap.getAllValues(PREFIX_ROLE).get(0).split("\\s+")).toList();
            RoleContainsKeywordsPredicate roleContainsKeywordsPredicate =
                    new RoleContainsKeywordsPredicate(roleKeywords);
            predicateContainer.addRoleContainsKeywordsPredicate(roleContainsKeywordsPredicate);
        }

        if (typeOfPerson.equals(FindEmployeeCommand.ARGUMENT_WORD)) {
            return new FindEmployeeCommand(predicateContainer);
        } else if (typeOfPerson.equals(FindPotentialCommand.ARGUMENT_WORD)) {
            return new FindPotentialCommand(predicateContainer);
        } else if (typeOfPerson.equals(FindCommand.ARGUMENT_WORD)) {
            return new FindCommand(predicateContainer);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
