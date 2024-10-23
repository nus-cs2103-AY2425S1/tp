package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.commons.RoleContainsKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE);

        if ((argMultimap.getValue(PREFIX_NAME).isEmpty() && argMultimap.getValue(PREFIX_ROLE).isEmpty())
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        assert argMultimap.getValue(PREFIX_NAME).isPresent() || argMultimap.getValue(PREFIX_ROLE).isPresent()
                : "Both name and role prefixes cannot be missing at the same time";

        Optional<String[]> nameKeywords = argMultimap.getValue(PREFIX_NAME).map(arg-> arg.split("\\s+"));
        Optional<Boolean> isValidNamesArg = nameKeywords.map(names -> Arrays.stream(names).allMatch(Name::isValidName));
        if (!isValidNamesArg.orElse(true)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        Optional<String> role = argMultimap.getValue(PREFIX_ROLE);
        Optional<Boolean> isValidRoleArg = role.map(Role::isValidRole);
        if (!isValidRoleArg.orElse(true)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }

        Predicate<Person> predicate = getPersonPredicate(nameKeywords, role);

        return new FindPersonCommand(predicate);
    }

    private Predicate<Person> getPersonPredicate(Optional<String[]> nameKeywords, Optional<String> role) {
        Predicate<Person> predicate = null;
        if (nameKeywords.isPresent() && nameKeywords.get().length > 0 && role.isPresent()) {
            predicate = new NameContainsKeywordsPredicate<Person>(Arrays.asList(nameKeywords.get()))
                    .and(new RoleContainsKeywordPredicate(role.get()));
        } else if (nameKeywords.isPresent() && nameKeywords.get().length > 0) {
            predicate = new NameContainsKeywordsPredicate<>(Arrays.asList(nameKeywords.get()));
        } else if (role.isPresent()) {
            predicate = new RoleContainsKeywordPredicate(role.get());
        }
        return predicate;
    }
}
