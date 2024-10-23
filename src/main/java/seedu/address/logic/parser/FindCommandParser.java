package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private PrefixHandler prefixHandler = new PrefixHandler();

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);


        String argumentType = prefixHandler.getArgumentType(argMultimap);
        switch (argumentType) {

            case "PHONE":
                Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
                return new FindCommand(phone);

            case "INDEX":
                Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new FindCommand(index);

            case "NAME":
                NameContainsKeywordsPredicate predicate =
                        new NameContainsKeywordsPredicate(argMultimap.getValue(PREFIX_NAME).get());
                return new FindCommand(predicate);
            case "ROLE":
                Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
                return new FindCommand(role);
            case "ADDRESS":
                Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
                return new FindCommand(address);
            case "EMAIL":
                Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
                return new FindCommand(email);
            case "TAG":
                Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
                return new FindCommand(tags);
            default:
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));


        }
    }


}
