package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BILLING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddCompanyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.company.Company;
import seedu.address.model.person.Phone;


/**
 * Parses input arguments and creates a new AddCompanyCommand object
 */
public class AddCompanyCommandParser implements Parser<AddCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCompanyCommand
     * and returns an AddCompanyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCompanyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_BILLING_DATE, PREFIX_PHONE);

        if (!ParserUtil.arePrefixesPresent(argMultiMap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_BILLING_DATE, PREFIX_PHONE)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCompanyCommand.MESSAGE_USAGE));
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_BILLING_DATE, PREFIX_PHONE);
        Name name = ParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get());
        Address address = ParserUtil.parseAddress(argMultiMap.getValue(PREFIX_ADDRESS).get());
        BillingDate date = ParserUtil.parseBillingDate(argMultiMap.getValue(PREFIX_BILLING_DATE).get());
        Phone phone = ParserUtil.parsePhone(argMultiMap.getValue(PREFIX_PHONE).get());

        Company company = new Company(name, address, date, phone);
        return new AddCompanyCommand(company);
    }

}
