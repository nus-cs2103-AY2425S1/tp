package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddOwnerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.owner.Address;
import seedu.address.model.owner.Email;
import seedu.address.model.owner.IdentificationCardNumber;
import seedu.address.model.owner.Name;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.Phone;

/**
 * Parses input arguments and creates a new AddOwnerCommand object
 */
public class AddOwnerCommandParser implements Parser<AddOwnerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOwnerCommand
     * and returns an AddOwnerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOwnerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_IC_NUMBER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        if (!arePrefixesPresent(argMultimap, PREFIX_IC_NUMBER, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOwnerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IC_NUMBER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
            PREFIX_ADDRESS);
        IdentificationCardNumber icNumber = ParserOwnerUtil.parseIcNumber(argMultimap.getValue(PREFIX_IC_NUMBER).get());
        Name name = ParserOwnerUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserOwnerUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserOwnerUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserOwnerUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());

        Owner owner = new Owner(icNumber, name, phone, email, address);

        return new AddOwnerCommand(owner);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
