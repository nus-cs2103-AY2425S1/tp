package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private AttributeParser attributeParser = new AttributeParser();

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        //defensive programming
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        String argumentType = attributeParser.getArgumentType(argMultimap);
        switch (argumentType) {

        case "PHONE":
            return parsePhone(argMultimap);
        case "INDEX":
            return parseIndex(argMultimap);
        case "NAME":
            return parseName(argMultimap);
        case "ADDRESS":
            return parseAddress(argMultimap);
        case "EMAIL":
            return parseEmail(argMultimap);
        case "TAG":
            return parseTags(argMultimap);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        }

    }

    /**
     * Parses the argument multimap to create a DeleteCommand using a phone number.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by phone number.
     * @throws ParseException if the phone number is invalid.
     */
    public DeleteCommand parsePhone(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Phone phone = attributeParser.parsePhone(argumentMultimap);
        return new DeleteCommand(phone);
    }

    /**
     * Parses the argument multimap to create a DeleteCommand using an index.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by index.
     * @throws ParseException if the index is invalid.
     */
    public DeleteCommand parseIndex(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Index index = attributeParser.parseIndex(argumentMultimap);
        return new DeleteCommand(index);
    }

    /**
     * Parses the argument multimap to create a DeleteCommand using a name.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by name.
     */
    public DeleteCommand parseName(ArgumentMultimap argumentMultimap) {
        NameContainsKeywordsPredicate predicate = attributeParser.parseName(argumentMultimap);
        return new DeleteCommand(predicate);
    }

    /**
     * Parses the argument multimap to create a DeleteCommand using an address.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by address.
     * @throws ParseException if the address is invalid.
     */
    public DeleteCommand parseAddress(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Address address = attributeParser.parseAddress(argumentMultimap);
        return new DeleteCommand(address);
    }

    /**
     * Parses the argument multimap to create a DeleteCommand using an email.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by email.
     * @throws ParseException if the email is invalid.
     */
    public DeleteCommand parseEmail(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Email email = attributeParser.parseEmail(argumentMultimap);
        return new DeleteCommand(email);
    }

    /**
     * Parses the argument multimap to create a DeleteCommand using tags.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A DeleteCommand for deleting by tags.
     * @throws ParseException if the tags are invalid.
     */
    public DeleteCommand parseTags(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Set<Tag> tags = attributeParser.parseTags(argumentMultimap);
        return new DeleteCommand(tags);
    }
}
