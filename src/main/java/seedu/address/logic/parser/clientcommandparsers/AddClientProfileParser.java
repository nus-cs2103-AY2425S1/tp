package seedu.address.logic.parser.clientcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.clientcommands.AddBuyerProfileCommand;
import seedu.address.logic.commands.clientcommands.AddSellerProfileCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.name.Name;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddClientProfileParser implements Parser<Command> {
    private final String role;

    public AddClientProfileParser(String commandWord) {
        role = commandWord;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        handleMissingPrefix(argMultimap);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        //  add command does not allow adding appointment straight away
        Appointment appointment = Appointment.EMPTY_APPOINTMENT;
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return createCommand(name, phone, email, appointment, tagList);
    }

    private void handleMissingPrefix(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasMissingPrefix = !ArgumentMultimap.arePrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        if (hasMissingPrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    role.equals("buyer")
                    ? AddBuyerProfileCommand.MESSAGE_USAGE
                    : AddSellerProfileCommand.MESSAGE_USAGE));
        }
    }

    private Command createCommand(Name name, Phone phone, Email email, Appointment appointment, Set<Tag> tagList) {

        if (role.equals("buyer")) {
            Buyer buyer = new Buyer(name, phone, email, tagList, appointment);
            return new AddBuyerProfileCommand(buyer);
        } else {
            Seller seller = new Seller(name, phone, email, tagList, appointment);
            return new AddSellerProfileCommand(seller);
        }
    }
}
