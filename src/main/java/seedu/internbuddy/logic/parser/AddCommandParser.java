package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;

import seedu.internbuddy.logic.commands.AddCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.company.Status;
import seedu.internbuddy.model.company.StatusType;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(null));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(null));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Status status = new Status(StatusType.INTERESTED);
        ArrayList<Application> applications = new ArrayList<>();

        Company company = new Company(name, phone, email, address, tagList, status,
                applications, false, false);

        return new AddCommand(company);
    }
}
