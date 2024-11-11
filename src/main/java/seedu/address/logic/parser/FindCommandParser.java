package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindPotentialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PredicateContainer;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    public static final String MESSAGE_NO_PREFIX = "Missing prefix! At least one prefix is required!";
    public static final String MESSAGE_NO_KEYWORD = "Missing keyword after prefix! It cannot be empty!";

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
        if (argMultimap.hasNoFindCommandPrefix()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FindCommandParser.MESSAGE_NO_PREFIX));
        }
        if (argMultimap.hasEmptyCommandPrefix()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, FindCommandParser.MESSAGE_NO_KEYWORD));
        }

        PredicateContainer predicateContainer = PredicateContainer.extractFromArgumentMultimap(argMultimap);

        if (typeOfPerson.equals(FindEmployeeCommand.ARGUMENT_WORD)) {
            return new FindEmployeeCommand(predicateContainer);
        } else if (typeOfPerson.equals(FindPotentialCommand.ARGUMENT_WORD)) {
            return new FindPotentialCommand(predicateContainer);
        } else if (typeOfPerson.equals(FindCommand.ARGUMENT_WORD)) {
            return new FindCommand(predicateContainer);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
