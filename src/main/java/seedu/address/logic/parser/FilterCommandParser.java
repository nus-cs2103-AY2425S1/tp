package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.PersonHasFeaturePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
 * and returns a FilterByTagCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
              ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);


        //you can only filter for one value per feature
        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1
                || argMultimap.getAllValues(PREFIX_PHONE).size() > 1
                || argMultimap.getAllValues(PREFIX_EMAIL).size() > 1
                || argMultimap.getAllValues(PREFIX_ADDRESS).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // At least one feature must be present for a valid filter
        if (!argMultimap.getValue(PREFIX_TAG).isPresent()
                && !argMultimap.getValue(PREFIX_PHONE).isPresent()
                && !argMultimap.getValue(PREFIX_EMAIL).isPresent()
                && !argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Tag tag = null;
        Phone phone = null;
        Email email = null;
        Address address = null;

        // Parse the values if they are present
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        }

        return new FilterCommand(new PersonHasFeaturePredicate(tag, phone, email, address));
    }

}
