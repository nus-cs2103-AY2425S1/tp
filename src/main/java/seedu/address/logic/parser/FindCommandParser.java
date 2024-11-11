package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private AttributeParser attributeParser = new AttributeParser();

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS);

        String argumentType = attributeParser.getArgumentType(argMultimap);
        switch (argumentType) {

        case "PHONE":
            return parsePhone(argMultimap);
        case "INDEX":
            return parseIndex(argMultimap);
        case "NAME":
            return parseName(argMultimap);
        case "ROLE":
            return parseRole(argMultimap);
        case "ADDRESS":
            return parseAddress(argMultimap);
        case "EMAIL":
            return parseEmail(argMultimap);
        case "TAG":
            return parseTags(argMultimap);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the argument multimap to create a FindCommand using a phone number.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by phone number.
     * @throws ParseException if the phone number is invalid.
     */
    public FindCommand parsePhone(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Phone phone = attributeParser.parsePhone(argumentMultimap);
        return new FindCommand(phone);
    }

    /**
     * Parses the argument multimap to create a FindCommand using an index.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by index.
     * @throws ParseException if the index is invalid.
     */
    public FindCommand parseIndex(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Index index = attributeParser.parseIndex(argumentMultimap);
        return new FindCommand(index);
    }

    /**
     * Parses the argument multimap to create a FindCommand using a name.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by name.
     */
    public FindCommand parseName(ArgumentMultimap argumentMultimap) {
        NameContainsKeywordsPredicate predicate = attributeParser.parseName(argumentMultimap);
        return new FindCommand(predicate);
    }

    /**
     * Parses the argument multimap to create a FindCommand using an address.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by address.
     * @throws ParseException if the address is invalid.
     */
    public FindCommand parseAddress(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Address address = attributeParser.parseAddress(argumentMultimap);
        return new FindCommand(address);
    }

    /**
     * Parses the argument multimap to create a FindCommand using an email.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by email.
     * @throws ParseException if the email is invalid.
     */
    public FindCommand parseEmail(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Email email = attributeParser.parseEmail(argumentMultimap);
        return new FindCommand(email);
    }

    /**
     * Parses the argument multimap to create a FindCommand using tags.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by tags.
     * @throws ParseException if the tags are invalid.
     */
    public FindCommand parseTags(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Set<Tag> tags = attributeParser.parseTags(argumentMultimap);
        return new FindCommand(tags);
    }

    /**
     * Parses the argument multimap to create a FindCommand using a role.
     *
     * @param argumentMultimap The ArgumentMultimap containing user input.
     * @return A FindCommand for searching by role.
     * @throws ParseException if the role is invalid.
     */
    public FindCommand parseRole(ArgumentMultimap argumentMultimap)
            throws ParseException {
        Role role = attributeParser.parseRole(argumentMultimap);
        return new FindCommand(role);
    }

}
