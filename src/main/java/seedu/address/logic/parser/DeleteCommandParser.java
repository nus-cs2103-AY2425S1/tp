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
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);


        String argumentType = getArgumentType(argMultimap);

        switch (argumentType) {

        case "PHONE":
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            return new DeleteCommand(phone);

        case "INDEX":
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new DeleteCommand(index);

        case "NAME":
            NameContainsKeywordsPredicate predicate =
                    new NameContainsKeywordsPredicate(argMultimap.getValue(PREFIX_NAME).get());
            return new DeleteCommand(predicate);
        case "ADDRESS":
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            return new DeleteCommand(address);
        case "EMAIL":
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            return new DeleteCommand(email);
        case "TAG":
            Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new DeleteCommand(tags);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));


        }

    }


    public String getArgumentType(ArgumentMultimap argMultimap) {

        if (!argMultimap.getPreamble().isEmpty()) {
            return "INDEX";
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return "NAME";
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
}
